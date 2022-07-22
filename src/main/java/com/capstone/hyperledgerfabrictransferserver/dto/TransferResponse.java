package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferResponse {

    private String transactionId;
    private String senderIdentifier;

    private String senderName;

    private String receiverIdentifier;

    private String receiverName;

    private String coinName;

    private Long amount;

    private LocalDateTime dateCreated;

    @Builder
    public TransferResponse(String transactionId, String senderIdentifier, String senderName, String receiverIdentifier, String receiverName, String coinName, Long amount, LocalDateTime dateCreated) {
        this.transactionId = transactionId;
        this.senderIdentifier = senderIdentifier;
        this.senderName = senderName;
        this.receiverIdentifier = receiverIdentifier;
        this.receiverName = receiverName;
        this.coinName = coinName;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }

    public static TransferResponse toDto(Trade trade) {
        return TransferResponse.builder()
                .transactionId(trade.getTransactionId())
                .senderIdentifier(trade.getSender().getIdentifier())
                .senderName(trade.getSender().getName())
                .receiverIdentifier(trade.getReceiver().getIdentifier())
                .receiverName(trade.getReceiver().getName())
                .coinName(trade.getCoin().getName())
                .amount(trade.getAmount())
                .dateCreated(trade.getDateCreated())
                .build();
    }
}