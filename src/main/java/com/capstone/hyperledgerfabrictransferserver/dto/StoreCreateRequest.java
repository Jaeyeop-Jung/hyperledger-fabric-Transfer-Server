package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreCreateRequest {

    private String storeName;
    private String phoneNumber;
    private String address;

    @Builder
    public StoreCreateRequest(String storeName, String phoneNumber, String address) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
