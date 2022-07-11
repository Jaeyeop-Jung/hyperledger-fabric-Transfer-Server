package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UpdateAssetCoinRequest {

    private Long studentId;
    private String coinName;
    private String coinValue;

    @Builder
    public UpdateAssetCoinRequest(Long studentId, String coinName, String coinValue) {
        this.studentId = studentId;
        this.coinName = coinName;
        this.coinValue = coinValue;
    }
}
