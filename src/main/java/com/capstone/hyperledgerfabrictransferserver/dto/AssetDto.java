package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;

import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AssetDto {

    private String assetId;

    private String owner;

    private HashMap<String, String> coin;

    private String sender;

    private String receiver;

    private String amount;

    @Builder
    public AssetDto(String assetId, String owner, HashMap<String, String> coin, String sender, String receiver, String amount) {
        this.assetId = assetId;
        this.owner = owner;
        this.coin = coin;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
