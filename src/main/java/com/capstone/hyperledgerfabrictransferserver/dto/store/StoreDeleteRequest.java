package com.capstone.hyperledgerfabrictransferserver.dto.store;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreDeleteRequest {

    private String name;
    private String phoneNumber;

    @Builder
    public StoreDeleteRequest(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
