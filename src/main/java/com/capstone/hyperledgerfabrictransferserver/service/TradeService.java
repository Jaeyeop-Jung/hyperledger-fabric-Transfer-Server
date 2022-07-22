package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectTransactionIdException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import com.capstone.hyperledgerfabrictransferserver.dto.RequestForGetTradeByDetails;
import com.capstone.hyperledgerfabrictransferserver.dto.PagingTransferResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final UserService userService;
    private final CoinService coinService;
    private final FabricService fabricService;
    private final TradeRepository tradeRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public TransferResponse transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest) {

        Coin requestedCoin = coinService.getByCoinName(transferRequest.getCoinName());
        User sender = userService.getUserByHttpServletRequest(httpServletRequest);
        User receiver = userService.getUserByIdentifier(transferRequest.getReceiverIdentifier());

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
        System.out.println("transferResponse = " + transferResponse);
        Trade savedTrade = tradeRepository.save(
                Trade.of(
                        transferResponse.getTransactionId(),
                        sender,
                        receiver,
                        requestedCoin,
                        transferRequest.getAmount()
                )
        );

        return TransferResponse.toDto(savedTrade);
    }

    @Transactional(readOnly = true)
    public PagingTransferResponseDto getAllTradeRelatedToIdentifier(HttpServletRequest httpServletRequest, int page) {

        User findUser = userService.getUserByHttpServletRequest(httpServletRequest);
        Page<Trade> findTradeList = tradeRepository.findAllBySenderOrReceiver(findUser, findUser, PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));

        return PagingTransferResponseDto.builder()
                .totalTradeNumber(findTradeList.getTotalElements())
                .totalPage(Long.valueOf(findTradeList.getTotalPages()))
                .transferResponseList(findTradeList.getContent().stream()
                        .map(trade -> TransferResponse.toDto(trade))
                        .collect(Collectors.toList())
                ).build();
    }

    @Transactional(readOnly = true)
    public PagingTransferResponseDto getAllTradeByDetails(
            int page,
            @NonNull RequestForGetTradeByDetails requestForGetTradeByDetails
    )  {
        User sender = userService.getUserByIdentifier(requestForGetTradeByDetails.getSenderIdentifier());
        User receiver = userService.getUserByIdentifier(requestForGetTradeByDetails.getReceiverIdentifier());

        Page<Trade> findTradeList = tradeRepository.findAllBySenderOrReceiverAndDateCreatedBetween(
                sender,
                receiver,
                requestForGetTradeByDetails.getFromLocalDateTime(),
                requestForGetTradeByDetails.getUntilLocalDateTime(),
                PageRequest.of(page - 1, 10, Sort.Direction.DESC, "dateCreated")
        );

        return PagingTransferResponseDto.builder()
                .totalTradeNumber(findTradeList.getTotalElements())
                .totalPage(Long.valueOf(findTradeList.getTotalPages()))
                .transferResponseList(findTradeList.getContent().stream()
                        .map(trade -> TransferResponse.toDto(trade))
                        .collect(Collectors.toList())
                ).build();
    }

    public TransferResponse getTradeByTransactionId(String transactionId) {
        Trade findTrade = tradeRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IncorrectTransactionIdException("조회하려고 하는 TransactionID가 없거나 잘못되었습니다"));
        return TransferResponse.toDto(findTrade);
    }
}
