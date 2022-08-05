package com.capstone.hyperledgerfabrictransferserver.dto.admin;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AdminLoginResponse {

    private String accessToken;

    @Builder
    public AdminLoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
