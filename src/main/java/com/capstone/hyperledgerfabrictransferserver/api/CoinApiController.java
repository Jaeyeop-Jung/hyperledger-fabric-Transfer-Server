package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface CoinApiController {

    public ResponseEntity<Void> create(HttpServletRequest httpServletRequest, CoinCreateRequest coinName);


}
