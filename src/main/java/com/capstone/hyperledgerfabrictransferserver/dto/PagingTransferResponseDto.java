package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PagingTransferResponseDto {

    private Long totalTradeNumber;

    private int totalPage;

    private List<TransferResponse> transferResponseList;

    @Builder
    public PagingTransferResponseDto(Long totalTradeNumber, int totalPage, List<TransferResponse> transferResponseList) {
        this.totalTradeNumber = totalTradeNumber;
        this.totalPage = totalPage;
        this.transferResponseList = transferResponseList;
    }
}
