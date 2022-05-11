package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserTradeApiController {

    ResponseEntity<Void> transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest);

}
