package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferRequest {

    @ApiParam(required = true)
    @NotNull
    private Long studentId;

    @ApiParam(required = true)
    @NotNull
    private Coin coin;

    @ApiParam(required = true)
    @NotNull
    private Long amount;

    @Builder
    public TransferRequest(Long studentId, Coin coin, Long amount) {
        this.studentId = studentId;
        this.coin = coin;
        this.amount = amount;
    }
}
