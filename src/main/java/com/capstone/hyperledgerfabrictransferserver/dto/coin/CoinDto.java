package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CoinDto {

    private String name;
    private Long issuance;

    @Builder
    public CoinDto(String name, Long issuance) {
        this.name = name;
        this.issuance = issuance;
    }

    public static CoinDto from(Coin coin) {
        return CoinDto.builder()
                .name(coin.getName())
                .issuance(coin.getIssuance())
                .build();
    }
}
