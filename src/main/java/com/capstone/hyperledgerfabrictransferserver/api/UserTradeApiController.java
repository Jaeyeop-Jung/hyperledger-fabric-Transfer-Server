package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserTradeApiController {

    ResponseEntity<Void> transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest);

}
