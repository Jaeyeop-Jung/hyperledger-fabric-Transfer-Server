package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TradeApiController {

    public ResponseEntity<TransferResponse> transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest);

    public ResponseEntity<List<TransferResponse>> enquireTrade(HttpServletRequest httpServletRequest, int page);
}
