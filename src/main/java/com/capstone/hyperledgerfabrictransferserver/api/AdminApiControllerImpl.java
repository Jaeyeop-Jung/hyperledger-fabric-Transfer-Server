package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.*;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiControllerImpl implements AdminApiController{

    private final UserService userService;
    private final TradeService tradeService;

    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
        @ModelAttribute UserLoginRequest userLoginRequest
    ){
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @Override
    @GetMapping("/users")
    public ResponseEntity<PagingUserDto> getAllUser(
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(userService.getAllUser(page));
    }

    @Override
    @GetMapping("/trade")
    public ResponseEntity<PagingTransferResponseDto> getAllTradeBy(
            @RequestParam(defaultValue = "1") int page,
            @ModelAttribute AllTransferRequest allTransferRequest
    ) {
        System.out.println("allTransferRequest = " + allTransferRequest);
        return ResponseEntity.ok(tradeService.getAllTradeBy(page, allTransferRequest));
    }
}
