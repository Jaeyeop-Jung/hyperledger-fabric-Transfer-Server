package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserJoinRequest {

    @ApiParam(required = true)
    @NotNull
    private Long studentId;

    @ApiParam(required = true)
    @NotNull
    private String password;

    @ApiParam(required = true)
    @NotNull
    private String name;

    @Builder
    public UserJoinRequest(Long studentId, String password, String name) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
    }

}
