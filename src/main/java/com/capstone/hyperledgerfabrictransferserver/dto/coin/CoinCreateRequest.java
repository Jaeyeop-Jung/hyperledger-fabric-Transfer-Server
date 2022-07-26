package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CoinCreateRequest {

    private String coinName;

    private Long issuance;

    @Builder
    public CoinCreateRequest(String coinName, Long issuance) {
        this.coinName = coinName;
        this.issuance = issuance;
    }
}
