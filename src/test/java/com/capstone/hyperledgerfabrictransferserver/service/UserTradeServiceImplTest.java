package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserTradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hyperledger.fabric.gateway.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTradeServiceImplTest {

    @InjectMocks
    UserTradeServiceImpl userTradeService;

    @Mock
    UserService userService;

    @Mock
    FabricService fabricService;

    @Mock
    UserRepository userRepository;

    @Mock
    CoinRepository coinRepository;

    @Mock
    UserTradeRepository userTradeRepository;

    @Spy
    ObjectMapper objectMapper;

    @Test
    @DisplayName("유저간 송금 테스트")
    void transfer_을_테스트한다() throws Exception{
        //given
        UserTransferRequest userTransferRequest = UserTransferRequest.builder()
                .studentId(2L)
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
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(submitTransactionResponse);

        //when
        TransferResponse transferResponse = userTradeService.transfer(httpServletRequest, userTransferRequest);

        //then
        Assertions.assertThat(transferResponse.getAmount()).isEqualTo(100L);

    }
}