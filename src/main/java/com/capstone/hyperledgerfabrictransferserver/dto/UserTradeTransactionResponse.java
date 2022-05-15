package com.capstone.hyperledgerfabrictransferserver.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserTradeTransactionResponse {
    @ApiParam(required = true)
    @NotNull
    private Long sender;

    @ApiParam(required = true)
    @NotNull
    private Long receiver;

    @ApiParam(required = true)
    @NotNull
    private String coinName;

    @ApiParam(required = true)
    @NotNull
    private Long amount;

    @ApiParam(required = true)
    @NotNull
    private LocalDateTime dateCreated;

    @Builder
    public UserTradeTransactionResponse(Long sender, Long receiver, String coinName, Long amount, LocalDateTime dateCreated) {
        this.sender = sender;
        this.receiver = receiver;
        this.coinName = coinName;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }
}
