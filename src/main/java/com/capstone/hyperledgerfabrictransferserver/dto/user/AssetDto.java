package com.capstone.hyperledgerfabrictransferserver.dto.user;

import lombok.*;

import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AssetDto {

    private String identifier;

    private String owner;

    private HashMap<String, String> coin;

    @Builder
    public AssetDto(String identifier, String owner, HashMap<String, String> coin) {
        this.identifier = identifier;
        this.owner = owner;
        this.coin = coin;
    }
}
