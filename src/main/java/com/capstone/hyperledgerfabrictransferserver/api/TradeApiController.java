package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.trade.PagingTradeResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TradeRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TradeApiController {

    private final TradeService tradeService;

    @PostMapping("/trade")
    public ResponseEntity<TransferResponse> transfer(
            HttpServletRequest httpServletRequest,
            @RequestBody TradeRequest tradeRequest
    )
    {
        return ResponseEntity.ok(tradeService.transfer(httpServletRequest, tradeRequest));
    }

    @GetMapping("/trade")
    public ResponseEntity<PagingTradeResponseDto> enquireTrade(HttpServletRequest httpServletRequest, @ApiParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(tradeService.getAllTradeRelatedToIdentifier(httpServletRequest, page));
    }

}
