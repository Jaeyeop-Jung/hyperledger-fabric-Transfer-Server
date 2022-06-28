package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferResponse {

    private Long senderStudentId;

    private String senderName;

    private Long receiverStudentIdOrPhoneNumber;

    private String receiverName;

    private String coinName;

    private Long amount;

    private LocalDateTime dateCreated;

    @Builder
    public TransferResponse(Long senderStudentId, String senderName, Long receiverStudentIdOrPhoneNumber, String receiverName, String coinName, Long amount, LocalDateTime dateCreated) {
        this.senderStudentId = senderStudentId;
        this.senderName = senderName;
        this.receiverStudentIdOrPhoneNumber = receiverStudentIdOrPhoneNumber;
        this.receiverName = receiverName;
        this.coinName = coinName;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }

    public static List<TransferResponse> toDtoList(List<Trade> tradeList){

        ArrayList<TransferResponse> transferResponseList = new ArrayList<>();
        for (Trade trade : tradeList) {
            TransferResponse transferResponse = TransferResponse.builder()
                    .senderStudentId(trade.getSender().getStudentId())
                    .senderName(trade.getSender().getName())
                    .receiverName(trade.getReceiver().getName())
                    .coinName(trade.getCoin().getName())
                    .amount(trade.getAmount())
                    .dateCreated(trade.getDateCreated())
                    .build();
            if(trade.getReceiver() != null){
                transferResponse.receiverStudentIdOrPhoneNumber = trade.getReceiver().getStudentId();
            } else {
                transferResponse.receiverStudentIdOrPhoneNumber = Long.valueOf(trade.getShop().getPhoneNumber());
            }

            transferResponseList.add(transferResponse);
        }

        return transferResponseList;
    }
}