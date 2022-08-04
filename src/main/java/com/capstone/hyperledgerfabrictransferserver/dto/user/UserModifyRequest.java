package com.capstone.hyperledgerfabrictransferserver.dto.user;

import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserModifyRequest {

    private String requestedIdentifier;
    private String wantToChangeIdentifier;
    private String wantToChangePlainPassword;
    private UserRole wantToChangeUserRole;
    private String wantToChangeName;

    @Builder
    public UserModifyRequest(String requestedIdentifier, String wantToChangeIdentifier, String wantToChangePlainPassword, UserRole wantToChangeUserRole, String wantToChangeName) {
        this.requestedIdentifier = requestedIdentifier;
        this.wantToChangeIdentifier = wantToChangeIdentifier;
        this.wantToChangePlainPassword = wantToChangePlainPassword;
        this.wantToChangeUserRole = wantToChangeUserRole;
        this.wantToChangeName = wantToChangeName;
    }
}
