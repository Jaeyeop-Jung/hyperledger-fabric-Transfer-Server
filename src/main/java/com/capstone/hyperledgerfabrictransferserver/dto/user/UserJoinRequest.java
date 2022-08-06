package com.capstone.hyperledgerfabrictransferserver.dto.user;

import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserJoinRequest {

    @ApiParam(required = true)
    @NotNull
    @Pattern(regexp = "(^2\\d{7})|(01\\d\\d{3,4}\\d{4})")
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
