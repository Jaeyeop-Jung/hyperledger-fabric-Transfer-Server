package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectTransactionIdException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import com.capstone.hyperledgerfabrictransferserver.dto.coin.DailyCoinTradingVolume;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.RequestForGetTradeByDetails;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.PagingTradeResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TradeRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferResponse;
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
import java.time.LocalDateTime;
import java.util.List;

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
    public TransferResponse transfer(HttpServletRequest httpServletRequest, TradeRequest tradeRequest) {

        Coin requestedCoin = coinService.getByCoinName(tradeRequest.getCoinName());
        User sender = userService.getUserByHttpServletRequest(httpServletRequest);
        User receiver = userService.getUserByIdentifier(tradeRequest.getReceiverIdentifier());

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
                    String.valueOf(tradeRequest.getAmount()));
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
                        tradeRequest.getAmount()
                )
        );

        return TransferResponse.from(savedTrade);
    }

    @Transactional(readOnly = true)
    public PagingTradeResponseDto getAllTradeRelatedToIdentifier(HttpServletRequest httpServletRequest, int page) {

        User findUser = userService.getUserByHttpServletRequest(httpServletRequest);
        Page<Trade> findTradeList = tradeRepository.findAllBySenderOrReceiver(findUser, findUser, PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));
        return PagingTradeResponseDto.from(findTradeList);
    }

    @Transactional(readOnly = true)
    public PagingTradeResponseDto getAllTradeByDetails(
            int page,
            @NonNull RequestForGetTradeByDetails requestForGetTradeByDetails
    )  {
        Page<Trade> findTradeList = tradeRepository.findAllBySenderOrReceiverAndDateCreatedBetween(
                requestForGetTradeByDetails.getSenderIdentifier(),
                requestForGetTradeByDetails.getReceiverIdentifier(),
                requestForGetTradeByDetails.getFromLocalDateTime(),
                requestForGetTradeByDetails.getUntilLocalDateTime(),
                requestForGetTradeByDetails.getSenderUserRole(),
                requestForGetTradeByDetails.getReceiverUserRole(),
                PageRequest.of(page - 1, 10, Sort.Direction.DESC, "dateCreated")
        );

        return PagingTradeResponseDto.from(findTradeList);
    }

    @Transactional(readOnly = true)
    public TransferResponse getTradeByTransactionId(String transactionId) {
        Trade findTrade = tradeRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IncorrectTransactionIdException("조회하려고 하는 TransactionID가 없거나 잘못되었습니다"));
        return TransferResponse.from(findTrade);
    }

    @Transactional(readOnly = true)
    public List<DailyCoinTradingVolume> getDailyCoinTradingVolume(String coinName, LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        Coin findCoin = coinService.getByCoinName(coinName);
        return tradeRepository.countDailyTradeByCoinBetween(findCoin, fromLocalDateTime, toLocalDateTime);
    }
}
