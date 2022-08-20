package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UpdateAssetCoinRequest {

    private List<String> identifier;
    private String coinName;
    private String coinValue;

    @Builder
    public UpdateAssetCoinRequest(List<String> identifier, String coinName, String coinValue) {
        this.identifier = identifier;
        this.coinName = coinName;
        this.coinValue = coinValue;
    }
}
