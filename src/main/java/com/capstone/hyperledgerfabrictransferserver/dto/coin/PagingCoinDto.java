package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PagingCoinDto {

    private Long totalCoinNumber;
    private int totalPage;
    private List<CoinDto> coinDtoList;


    @Builder
    public PagingCoinDto(Long totalCoinNumber, int totalPage, List<CoinDto> coinDtoList) {
        this.totalCoinNumber = totalCoinNumber;
        this.totalPage = totalPage;
        this.coinDtoList = coinDtoList;
    }

    public static PagingCoinDto from(Page<Coin> coins) {
        return PagingCoinDto.builder()
                .totalPage(coins.getTotalPages())
                .totalCoinNumber(coins.getTotalElements())
                .coinDtoList(
                        coins.getContent().stream()
                                .map(coin -> CoinDto.from(coin))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
