package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface CoinApiController {

    public ResponseEntity<Void> create(CoinCreateRequest coinCreateRequest);

    public ResponseEntity<Void> delete(CoinModifyRequest coinModifyRequest);
}
