package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserTradeApiControllerImpl implements UserTradeApiController{


    @Override
    @PostMapping("/usertrade")
    public ResponseEntity<Void> transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest) {
        return null;
    }
}
