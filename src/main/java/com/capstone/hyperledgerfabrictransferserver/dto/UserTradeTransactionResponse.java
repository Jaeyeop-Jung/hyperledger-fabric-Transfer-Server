package com.capstone.hyperledgerfabrictransferserver.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserTradeTransactionResponse {

    @NotNull
    private Long sender;

    @NotNull
    private Long receiver;

    @NotNull
    private String coinName;

    @NotNull
    private Long amount;

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
