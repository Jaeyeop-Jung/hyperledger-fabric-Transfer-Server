package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.capstone.hyperledgerfabrictransferserver.service.UserTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserTradeApiControllerImpl implements UserTradeApiController{

    private final UserTradeService userTradeService;

    @Override
    @PostMapping("/usertrade")
    public ResponseEntity<Void> transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest) {

        userTradeService.transfer(httpServletRequest, transferRequest);

        return ResponseEntity.ok(null);
    }
}
