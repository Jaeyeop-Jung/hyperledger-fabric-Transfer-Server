package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CoinModifyRequest {

    private String coinName;

    @Builder
    public CoinModifyRequest(String coinName) {
        this.coinName = coinName;
    }
}
