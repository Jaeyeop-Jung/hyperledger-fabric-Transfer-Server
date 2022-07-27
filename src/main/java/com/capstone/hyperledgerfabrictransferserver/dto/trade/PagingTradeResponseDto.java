package com.capstone.hyperledgerfabrictransferserver.dto.trade;

import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PagingTradeResponseDto {

    private Long totalTradeNumber;

    private int totalPage;

    private List<TransferResponse> transferResponseList;

    @Builder
    private PagingTradeResponseDto(Long totalTradeNumber, int totalPage, List<TransferResponse> transferResponseList) {
        this.totalTradeNumber = totalTradeNumber;
        this.totalPage = totalPage;
        this.transferResponseList = transferResponseList;
    }

    public static PagingTradeResponseDto from(Page<Trade> trades) {
        return PagingTradeResponseDto.builder()
                .totalTradeNumber(trades.getTotalElements())
                .totalPage(trades.getTotalPages())
                .transferResponseList(
                        trades.getContent().stream()
                                .map(trade -> TransferResponse.from(trade))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
