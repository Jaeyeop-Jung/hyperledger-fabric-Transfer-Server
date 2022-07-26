package com.capstone.hyperledgerfabrictransferserver.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserLoginResponse {

    private String accessToken;

    @Builder
    public UserLoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
