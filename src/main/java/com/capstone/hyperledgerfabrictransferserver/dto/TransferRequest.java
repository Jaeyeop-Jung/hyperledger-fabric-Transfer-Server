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
    private String receiverUniqueNumber;

    @ApiParam(required = true)
    @NotNull
    private String coinName;

    @ApiParam(required = true)
    @NotNull
    private Long amount;

    @Builder
    public TransferRequest(String receiverUniqueNumber, String coinName, Long amount) {
        this.receiverUniqueNumber = receiverUniqueNumber;
        this.coinName = coinName;
        this.amount = amount;
    }
}
