package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TradeApiController {

    private final TradeService tradeService;

    @PostMapping("/trade")
    public ResponseEntity<TransferResponse> transfer(
            HttpServletRequest httpServletRequest,
            @RequestBody TransferRequest transferRequest
    )
    {
        System.out.println("transferRequest = " + transferRequest);
        return ResponseEntity.ok(tradeService.transfer(httpServletRequest, transferRequest));
    }

    @GetMapping("/trade")
    public ResponseEntity<List<TransferResponse>> enquireTrade(HttpServletRequest httpServletRequest, int page) {
        return ResponseEntity.ok(tradeService.enquireTrade(httpServletRequest, page));
    }

}
