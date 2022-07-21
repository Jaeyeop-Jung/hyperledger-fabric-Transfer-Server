package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectTransactionIdException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import com.capstone.hyperledgerfabrictransferserver.dto.RequestForGetTradeByDetails;
import com.capstone.hyperledgerfabrictransferserver.dto.PagingTransferResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final UserService userService;
    private final FabricService fabricService;
    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private final TradeRepository tradeRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public TransferResponse transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest) {

        Coin requestedCoin = coinRepository.findByName(transferRequest.getCoinName())
                .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인입니다"));
        User sender = userService.getUserByJwtToken(httpServletRequest);
        User receiver = userService.getUserByUniqueNumber(transferRequest.getReceiverUniqueNumber());

        String fabricResponse;
        TransferResponse transferResponse;
        try {
            Gateway gateway = fabricService.getGateway();
            fabricResponse = fabricService.submitTransaction(
                    gateway,
                    "TransferCoin",
                    "asset" + sender.getId(),
                    "asset" + receiver.getId(),
                    requestedCoin.getName(),
                    String.valueOf(transferRequest.getAmount()));
            fabricService.close(gateway);

            transferResponse = objectMapper.readValue(fabricResponse, TransferResponse.class);
        } catch (Exception e) {
            throw new IncorrectContractException("TransferCoin 체인코드 실행 중 오류가 발생했습니다");
        }

        Trade save = tradeRepository.save(
                Trade.of(
                        transferResponse.getTransactionId(),
                        sender,
                        receiver,
                        requestedCoin,
                        transferRequest.getAmount()
                )
        );

        return tradeRepository.findTradeById(save.getId());
    }

    @Transactional(readOnly = true)
    public PagingTransferResponseDto getTradeRelatedToUniqueNumber(HttpServletRequest httpServletRequest, int page) {

        User findUser = userService.getUserByUniqueNumber(httpServletRequest);
        Page<TransferResponse> findAllTransferResponse = tradeRepository.findAllBySenderOrReceiver(findUser, findUser, PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));

        return PagingTransferResponseDto.builder()
                .totalTradeNumber(findAllTransferResponse.getTotalElements())
                .totalPage(findAllTransferResponse.getTotalPages())
                .transferResponseList(findAllTransferResponse.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public PagingTransferResponseDto getAllTradeBy(
            int page,
            RequestForGetTradeByDetails requestForGetTradeByDetails
    )  {
        User sender = userService.getUserByUniqueNumber(requestForGetTradeByDetails.getSenderUniqueNumber());
        User receiver = userService.getUserByUniqueNumber(requestForGetTradeByDetails.getReceiverUniqueNumber());

        Page<TransferResponse> findAllTransferResponse = tradeRepository.findAllBySenderOrReceiverBetween(
                requestForGetTradeByDetails.getFromLocalDateTime(),
                requestForGetTradeByDetails.getUntilLocalDateTime(),
                sender,
                receiver,
                PageRequest.of(page - 1, 10, Sort.Direction.DESC, "dateCreated")
        );

        return PagingTransferResponseDto.builder()
                .totalTradeNumber(findAllTransferResponse.getTotalElements())
                .totalPage(findAllTransferResponse.getTotalPages())
                .transferResponseList(findAllTransferResponse.getContent())
                .build();
    }

    public TransferResponse getTradeByTransactionId(String transactionId) {
        Trade findTrade = tradeRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IncorrectTransactionIdException("조회하려고 하는 TransactionID가 없거나 잘못되었습니다"));
        return null;
    }
}
