package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTradeTransactionResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.capstone.hyperledgerfabrictransferserver.service.UserTradeService;
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
public class UserTradeApiControllerImpl implements UserTradeApiController{

    private final UserTradeService userTradeService;

    @PostMapping("/usertrade")
    public ResponseEntity<Void> transfer
    (
            HttpServletRequest httpServletRequest,
            @RequestBody UserTransferRequest transferRequest
    )
    {

        userTradeService.transfer(httpServletRequest, transferRequest);

        return ResponseEntity.ok(null);
    }


    @Override
    @GetMapping("/usertrade")
    public ResponseEntity<List<UserTradeTransactionResponse>> enquireUserTrade(HttpServletRequest httpServletRequest){
        List<UserTradeTransactionResponse> resposne = userTradeService.transaction(httpServletRequest);

        return ResponseEntity.ok(resposne);
    }
}
