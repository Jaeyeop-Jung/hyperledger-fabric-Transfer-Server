package com.capstone.hyperledgerfabrictransferserver.dto.user;

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
    private String identifier;

    @ApiParam(required = true)
    @NotNull
    private String password;

    @ApiParam(required = true)
    @NotNull
    private UserRole userRole;

    @ApiParam(required = true)
    @NotNull
    private String name;

    @Builder
    public UserJoinRequest(String identifier, String password, UserRole userRole, String name) {
        this.identifier = identifier;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }
}
