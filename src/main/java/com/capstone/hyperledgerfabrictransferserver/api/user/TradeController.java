package com.capstone.hyperledgerfabrictransferserver.api.user;

import com.capstone.hyperledgerfabrictransferserver.dto.trade.PagingTradeResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TradeRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class TradeController {

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
