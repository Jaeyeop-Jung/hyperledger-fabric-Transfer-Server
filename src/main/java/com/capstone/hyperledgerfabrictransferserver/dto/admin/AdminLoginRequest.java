package com.capstone.hyperledgerfabrictransferserver.dto.admin;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AdminLoginRequest {

    private String email;
    private String password;

    @Builder
    public AdminLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
