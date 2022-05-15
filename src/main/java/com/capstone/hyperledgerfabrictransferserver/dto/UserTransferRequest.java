package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserTransferRequest {

    @ApiParam(required = true)
    @NotNull
    private Long receiverStudentId;

    @ApiParam(required = true)
    @NotNull
    private String coinName;

    @ApiParam(required = true)
    @NotNull
    private Long amount;

    @Builder
    public UserTransferRequest(Long receiverStudentId, String coinName, Long amount) {
        this.receiverStudentId = receiverStudentId;
        this.coinName = coinName;
        this.amount = amount;
    }
}
