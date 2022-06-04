package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

    @InjectMocks
    TradeServiceImpl tradeService;

    @Mock
    UserService userService;

    @Mock
    FabricService fabricService;

    @Mock
    UserRepository userRepository;

    @Mock
    CoinRepository coinRepository;

    @Mock
    TradeRepository tradeRepository;

    @Spy
    ObjectMapper objectMapper;

    @Test
    @DisplayName("유저간 송금 테스트")
    void transfer_을_테스트한다() throws Exception{
        //given
        TransferRequest transferRequest = TransferRequest.builder()
                .receiverStudentIdOrPhoneNumber(2L)
                .coinName("test")
                .amount(100L)
                .build();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Coin coin = Coin.of("test");
        User sender = User.of(
                1L,
                "test",
                UserRole.ROLE_USER,
                "test"
        );
        User receiver = User.of(
                2L,
                "test",
                UserRole.ROLE_USER,
                "test2"
        );
        Trade trade = Trade.of(
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
                        .senderStudentId(1L)
                        .receiverStudentIdOrPhoneNumber(2L)
                        .coinName("test")
                        .amount(100L)
                        .build()
        );

        when(coinRepository.findByName(any()))
                .thenReturn(Optional.of(coin));
        when(userService.getUserByJwtToken(any()))
                .thenReturn(sender);
        when(userRepository.findByStudentId(any()))
                .thenReturn(Optional.of(receiver));
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
                1L,
                "test",
                UserRole.ROLE_USER,
                "test1"
        );
        User receiver = User.of(
                2L,
                "test",
                UserRole.ROLE_USER,
                "test2"
        );
        Coin coin = Coin.of("test");
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        List<Trade> tradeList = new ArrayList<>();
        Trade trade = Trade.of(
                sender,
                receiver,
                coin,
                100L
        );
        tradeList.add(trade);
        List<TransferResponse> transferResponseList = new ArrayList<>();
        transferResponseList.add(
                TransferResponse.builder()
                        .senderStudentId(sender.getStudentId())
                        .receiverStudentIdOrPhoneNumber(receiver.getStudentId())
                        .coinName(coin.getName())
                        .amount(trade.getAmount())
                        .build()
        );

        when(userService.getUserByJwtToken(any()))
                .thenReturn(sender);
        when(tradeRepository.findAllBySenderOrReceiver(any(), any(), any()))
                .thenReturn(new PageImpl<Trade>(tradeList));

        //when
        List<TransferResponse> response = tradeService.enquireTrade(httpServletRequest, 1);

        //then
        verify(userService).getUserByJwtToken(any());
        verify(tradeRepository).findAllBySenderOrReceiver(any(), any(), any());
        assertThat(response.get(0).getSenderStudentId()).isEqualTo(sender.getStudentId());
        assertThat(response.get(0).getReceiverStudentIdOrPhoneNumber()).isEqualTo(receiver.getStudentId());
        assertThat(response.get(0).getCoinName()).isEqualTo(trade.getCoin().getName());

    }
}