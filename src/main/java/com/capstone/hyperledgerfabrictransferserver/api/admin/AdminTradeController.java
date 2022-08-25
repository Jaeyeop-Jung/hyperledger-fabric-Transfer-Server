package com.capstone.hyperledgerfabrictransferserver.api.admin;

import com.capstone.hyperledgerfabrictransferserver.dto.coin.DailyCoinTradingVolume;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.PagingTradeResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.RequestForGetTradeByDetails;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminTradeController {

    private final TradeService tradeService;

    @GetMapping("/trade")
    public ResponseEntity<PagingTradeResponseDto> getAllTradeByDetails(
            @RequestParam(defaultValue = "1") int page,
            @ModelAttribute RequestForGetTradeByDetails requestForGetTradeByDetails
    ) {
        return ResponseEntity.ok(tradeService.getAllTradeByDetails(page, requestForGetTradeByDetails));
    }

    @GetMapping("/trade/{transactionId}")
    public ResponseEntity<TransferResponse> getTradeByTransactionId(@PathVariable @NonNull String transactionId) {
        return ResponseEntity.ok(tradeService.getTradeByTransactionId(transactionId));
    }

    @GetMapping("/trades/coin")
    public ResponseEntity<List<DailyCoinTradingVolume>> getDailyCoinTradingVolume(
            @RequestParam String coinName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromLocalDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toLocalDateTime
    ) {
        return ResponseEntity.ok(tradeService.getDailyCoinTradingVolume(coinName, fromLocalDateTime, toLocalDateTime));
    }

}
