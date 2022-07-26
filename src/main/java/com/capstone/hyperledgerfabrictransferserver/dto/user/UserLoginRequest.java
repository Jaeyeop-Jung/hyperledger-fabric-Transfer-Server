package com.capstone.hyperledgerfabrictransferserver.dto.user;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserLoginRequest {

    @ApiParam(required = true)
    @NotNull
    private String identifier;

    @ApiParam(required = true)
    @NotNull
    private String password;

    @Builder
    public UserLoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

}
