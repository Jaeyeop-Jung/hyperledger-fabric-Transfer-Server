package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CoinApiControllerImpl implements CoinApiController{

    private final CoinService coinService;

    @Override
    @PostMapping("/coin")
    public ResponseEntity<Void> create(
            HttpServletRequest httpServletRequest,
            @RequestBody CoinCreateRequest coinCreateRequest
    )
    {

        coinService.create(coinCreateRequest);

        return ResponseEntity.ok(null);
    }

}
