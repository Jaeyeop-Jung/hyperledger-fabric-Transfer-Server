package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;

import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AssetDto {

    private Long studentId;

    private String owner;

    private HashMap<String, String> coin;

    private String sender; // 필요한가?

    private String receiver; //

    private Long amount; //

    @Builder
    public AssetDto(Long studentId, String owner, HashMap<String, String> coin, String sender, String receiver, Long amount) {
        this.studentId = studentId;
        this.owner = owner;
        this.coin = coin;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
