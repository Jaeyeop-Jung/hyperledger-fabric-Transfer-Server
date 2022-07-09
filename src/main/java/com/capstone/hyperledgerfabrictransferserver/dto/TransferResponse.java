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
    private Long senderStudentId;

    private String senderName;

    private Long receiverStudentIdOrPhoneNumber;

    private String receiverName;

    private String coinName;

    private Long amount;

    private LocalDateTime dateCreated;

    @Builder
    public TransferResponse(String transactionId, Long senderStudentId, String senderName, Long receiverStudentIdOrPhoneNumber, String receiverName, String coinName, Long amount, LocalDateTime dateCreated) {
        this.transactionId = transactionId;
        this.senderStudentId = senderStudentId;
        this.senderName = senderName;
        this.receiverStudentIdOrPhoneNumber = receiverStudentIdOrPhoneNumber;
        this.receiverName = receiverName;
        this.coinName = coinName;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }

    public static List<TransferResponse> toDtoList(List<Trade> tradeList){
        return tradeList.stream()
                .map(trade -> {
                    TransferResponse build = TransferResponse.builder()
                            .transactionId(trade.getTransactionId())
                            .senderStudentId(trade.getSender().getStudentId())
                            .senderName(trade.getSender().getName())
                            .receiverName(trade.getReceiver().getName())
                            .coinName(trade.getCoin().getName())
                            .amount(trade.getAmount())
                            .dateCreated(trade.getDateCreated())
                            .build();
                    build.receiverStudentIdOrPhoneNumber = (trade.getReceiver() != null) ?
                            trade.getReceiver().getStudentId() : Long.valueOf(trade.getShop().getPhoneNumber());
                    return build;
                })
                .collect(Collectors.toList());
    }
}