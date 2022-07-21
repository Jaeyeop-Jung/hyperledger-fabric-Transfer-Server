package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferResponse {

    private String transactionId;
    private String senderUniqueNumber;

    private String senderName;

    private String receiverUniqueNumber;

    private String receiverName;

    private String coinName;

    private Long amount;

    private LocalDateTime dateCreated;

    @Builder
    public TransferResponse(String transactionId, String senderUniqueNumber, String senderName, String receiverUniqueNumber, String receiverName, String coinName, Long amount, LocalDateTime dateCreated) {
        this.transactionId = transactionId;
        this.senderUniqueNumber = senderUniqueNumber;
        this.senderName = senderName;
        this.receiverUniqueNumber = receiverUniqueNumber;
        this.receiverName = receiverName;
        this.coinName = coinName;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }
}