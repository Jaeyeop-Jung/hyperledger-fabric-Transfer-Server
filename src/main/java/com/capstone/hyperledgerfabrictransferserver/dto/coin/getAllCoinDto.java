package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class getAllCoinDto {

    private Long totalIssuance;

    private List<CoinDto> coinDtoList;


    @Builder
    public getAllCoinDto(Long totalIssuance, List<CoinDto> coinDtoList) {
        this.totalIssuance = totalIssuance;
        this.coinDtoList = coinDtoList;
    }


    public static getAllCoinDto from(List<Coin> coinList) {
        return getAllCoinDto.builder()
                .coinDtoList(
                        coinList.stream()
                                .map(CoinDto::from)
                                .collect(Collectors.toList())
                )
                .totalIssuance(
                        coinList.stream().
                                mapToLong(Coin::getIssuance)
                                .sum()
                )
                .build();
    }
}
