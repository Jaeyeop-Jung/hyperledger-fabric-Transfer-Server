package com.capstone.hyperledgerfabrictransferserver.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserLoginRequest {

    @ApiParam(required = true)
    @NotNull
    private Long studentId;

    @ApiParam(required = true)
    @NotNull
    private String password;

    @Builder
    public UserLoginRequest(Long studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }

}
