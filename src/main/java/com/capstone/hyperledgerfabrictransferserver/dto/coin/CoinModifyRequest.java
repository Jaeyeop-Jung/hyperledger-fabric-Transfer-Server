package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CoinModifyRequest {

    private List<String> coinNameList;

    @Builder
    public CoinModifyRequest(List<String> coinNameList) {
        this.coinNameList = coinNameList;
    }
}
