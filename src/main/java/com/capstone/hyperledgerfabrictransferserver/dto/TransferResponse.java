package com.capstone.hyperledgerfabrictransferserver.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TransferResponse {

    private Long senderStudentId;

    private Long receiverStudentIdOrPhoneNumber;

    private String coinName;

    private Long amount;

    private LocalDateTime dateCreated;

    @Builder
    public TransferResponse(Long senderStudentId, Long receiverStudentIdOrPhoneNumber, String coinName, Long amount, LocalDateTime dateCreated) {
        this.senderStudentId = senderStudentId;
        this.receiverStudentIdOrPhoneNumber = receiverStudentIdOrPhoneNumber;
        this.coinName = coinName;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }
}