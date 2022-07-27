package com.capstone.hyperledgerfabrictransferserver.dto.store;

import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class GetStoreResponse {

    private String name;
    private String phoneNumber;
    private String address;
    private String storeImageFileName;

    @Builder
    public GetStoreResponse(String name, String phoneNumber, String address, String storeImageFileName) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.storeImageFileName = storeImageFileName;
    }

    public static GetStoreResponse from(Store store) {
        return GetStoreResponse.builder()
                .name(store.getName())
                .phoneNumber(store.getPhoneNumber())
                .address(store.getAddress())
                .storeImageFileName(store.getStoreImage().getName())
                .build();
    }
}
