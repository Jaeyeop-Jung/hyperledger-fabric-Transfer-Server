package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.*;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.PagingTransferResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferResponse;import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.gateway.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

    @InjectMocks
    TradeService tradeService;

    @Mock
    UserService userService;

    @Mock
    CoinService coinService;

    @Mock
    FabricService fabricService;

    @Mock
    TradeRepository tradeRepository;

    @Spy
    ObjectMapper objectMapper;

    @Test
    @DisplayName("유저간 송금 테스트")
    void transfer_을_테스트한다() throws Exception{
        //given
        TransferRequest transferRequest = TransferRequest.builder()
                .receiverIdentifier("2")
                .coinName("test")
                .amount(100L)
                .build();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Coin coin = Coin.of("test", 1000L);
        User sender = User.of(
                "1",
                "test",
                UserRole.ROLE_STUDENT,
                "test1"
        );
        User receiver = User.of(
                "2",
                "test",
                UserRole.ROLE_STUDENT,
                "test2"
        );
        Trade trade = Trade.of(
                "test",
                sender,
                receiver,
                coin,
                100L
        );
        Gateway gateway = mock(Gateway.class);
        HashMap<String, String> coinMap = new HashMap<>();
        coinMap.put("test", "100");
        String submitTransactionResponse = objectMapper.writeValueAsString(
                TransferResponse.builder()
                        .transactionId("test")
                        .senderIdentifier("1")
                        .senderName("test1")
                        .senderIdentifier("2")
                        .receiverName("test2")
                        .coinName("test")
                        .amount(100L)
                        .build()
        );

        when(coinService.getByCoinName(any()))
                .thenReturn(coin);
        when(userService.getUserByHttpServletRequest(any()))
                .thenReturn(sender);
        when(userService.getUserByIdentifier(any()))
                .thenReturn(receiver);
        when(tradeRepository.save(any()))
                .thenReturn(trade);
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(submitTransactionResponse);

        //when
        TransferResponse transferResponse = tradeService.transfer(httpServletRequest, transferRequest);

        //then
        assertThat(transferResponse.getAmount()).isEqualTo(100L);
    }

    @Test
    public void enquireTrade_를_테스트한다() throws Exception {
        //given
        User sender = User.of(
                "1",
                "test",
                UserRole.ROLE_STUDENT,
                "test1"
        );
        User receiver = User.of(
                "2",
                "test",
                UserRole.ROLE_STUDENT,
                "test2"
        );
        Coin coin = Coin.of("test", 1000L);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Trade trade = Trade.of(
                "test",
                sender,
                receiver,
                coin,
                100L
        );
        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(
                Trade.of(
                        "test",
                        sender,
                        receiver,
                        coin,
                        100L
                )
        );

        when(userService.getUserByHttpServletRequest(any()))
                .thenReturn(sender);
        when(tradeRepository.findAllBySenderOrReceiver(any(), any(), any()))
                .thenReturn(new PageImpl<>(tradeList));

        //when
        PagingTransferResponseDto response = tradeService.getAllTradeRelatedToIdentifier(httpServletRequest, 1);

        //then
        verify(userService).getUserByHttpServletRequest(any());
        verify(tradeRepository).findAllBySenderOrReceiver(any(), any(), any());
        assertThat(response.getTransferResponseList().get(0).getSenderName()).isEqualTo(sender.getName());
        assertThat(response.getTransferResponseList().get(0).getReceiverName()).isEqualTo(receiver.getName());
        assertThat(response.getTransferResponseList().get(0).getCoinName()).isEqualTo(trade.getCoin().getName());

    }
}