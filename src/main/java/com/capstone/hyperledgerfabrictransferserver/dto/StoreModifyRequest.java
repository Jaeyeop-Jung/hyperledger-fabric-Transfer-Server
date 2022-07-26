package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreModifyRequest {

    private String name;
    private String phoneNumber;

    @Builder
    public StoreModifyRequest(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
