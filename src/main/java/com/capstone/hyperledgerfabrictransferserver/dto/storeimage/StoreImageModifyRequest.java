package com.capstone.hyperledgerfabrictransferserver.dto.storeimage;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class StoreImageModifyRequest {

    private String name;
    private String phoneNumber;

    @Builder
    public StoreImageModifyRequest(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
