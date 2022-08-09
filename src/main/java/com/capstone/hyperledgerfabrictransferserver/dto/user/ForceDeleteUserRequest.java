package com.capstone.hyperledgerfabrictransferserver.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ForceDeleteUserRequest {

    private String identifier;

    @Builder
    public ForceDeleteUserRequest(String identifier) {
        this.identifier = identifier;
    }
}
