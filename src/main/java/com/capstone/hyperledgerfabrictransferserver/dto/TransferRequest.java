package com.capstone.hyperledgerfabrictransferserver.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferRequest {

    @ApiParam(required = true)
    @NotNull
    private Long receiverStudentIdOrPhoneNumber;

    @ApiParam(required = true)
    @NotNull
    private String coinName;

    @ApiParam(required = true)
    @NotNull
    private Long amount;

    @Builder
    public TransferRequest(Long receiverStudentIdOrPhoneNumber, String coinName, Long amount) {
        this.receiverStudentIdOrPhoneNumber = receiverStudentIdOrPhoneNumber;
        this.coinName = coinName;
        this.amount = amount;
    }
}
