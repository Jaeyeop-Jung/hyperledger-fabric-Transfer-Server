package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UpdateAssetCoinRequest {

    private String identifier;
    private String coinName;
    private String coinValue;

    @Builder
    public UpdateAssetCoinRequest(String identifier, String coinName, String coinValue) {
        this.identifier = identifier;
        this.coinName = coinName;
        this.coinValue = coinValue;
    }
}
