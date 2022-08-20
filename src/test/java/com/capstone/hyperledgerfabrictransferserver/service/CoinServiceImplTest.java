package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.dto.coin.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.coin.CoinModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import org.hyperledger.fabric.gateway.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinServiceImplTest {

    @InjectMocks
    CoinService coinService;

    @Mock
    FabricService fabricService;

    @Mock
    CoinRepository coinRepository;

    @Test
    @DisplayName("코인 생성 테스트")
    void create_를_테스트한다() throws Exception{
        //given
        CoinCreateRequest coinCreateRequest = CoinCreateRequest.builder()
                .coinName("test")
                .build();
        Gateway gateway = mock(Gateway.class);

        when(coinRepository.existsByName(any()))
                .thenReturn(false);
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(String.valueOf("true"));

        //when
        coinService.create(coinCreateRequest);

        //then
        verify(coinRepository).existsByName(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
    }

    @Test
    @DisplayName("코인 삭제 테스트")
    void delete_를_테스트한다() throws Exception{
        //given
        CoinModifyRequest coinModifyRequest = CoinModifyRequest.builder()
                .coinNameList(Arrays.asList("test"))
                .build();
        Coin coin = Coin.of("test", 1000L);
        Gateway gateway = mock(Gateway.class);

        when(coinRepository.findByName(any()))
                .thenReturn(Optional.of(coin));
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(String.valueOf("true"));

        //when
        coinService.delete(coinModifyRequest);

        //then
        verify(coinRepository).findByName(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
        assertThat(coin.isDeleted()).isTrue();
        assertThat(coin.getName()).isNotEqualTo("test");
    }
}