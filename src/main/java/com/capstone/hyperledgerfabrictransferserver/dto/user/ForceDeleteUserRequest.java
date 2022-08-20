package com.capstone.hyperledgerfabrictransferserver.dto.user;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ForceDeleteUserRequest {

    private List<String> identifier;

    @Builder
    public ForceDeleteUserRequest(List<String> identifier) {
        this.identifier = identifier;
    }
}
