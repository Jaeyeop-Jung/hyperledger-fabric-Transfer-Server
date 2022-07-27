package com.capstone.hyperledgerfabrictransferserver.dto.store;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CreateStoreRequest {

    private String storeName;
    private String phoneNumber;
    private String address;

    @Builder
    public CreateStoreRequest(String storeName, String phoneNumber, String address) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
