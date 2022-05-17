package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferResponse {

    private Long senderStudentId;

    private Long receiverStudentId;

    private String coinName;

    private Long amount;

    @Builder
    public TransferResponse(Long senderStudentId, Long receiverStudentId, String coinName, Long amount) {
        this.senderStudentId = senderStudentId;
        this.receiverStudentId = receiverStudentId;
        this.coinName = coinName;
        this.amount = amount;
    }
}