package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UpdateAllAssetCoinRequest {

    private UserRole userRole;
    private String coinName;
    private String coinValue;

    @Builder
    public UpdateAllAssetCoinRequest(UserRole userRole, String coinName, String coinValue) {
        this.userRole = userRole;
        this.coinName = coinName;
        this.coinValue = coinValue;
    }
}
