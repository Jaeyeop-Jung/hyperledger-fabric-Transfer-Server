package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.*;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final UserService userService;
    private final TradeService tradeService;

    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
        @ModelAttribute UserLoginRequest userLoginRequest
    ){
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<PagingUserDto> getAllUser(
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(userService.getAllUser(page));
    }

    @GetMapping("/trade")
    public ResponseEntity<PagingTransferResponseDto> getAllTradeByDetails(
            @RequestParam(defaultValue = "1") int page,
            @ModelAttribute RequestForGetTradeByDetails requestForGetTradeByDetails
    ) {
        System.out.println("requestForGetTradeByDetails = " + requestForGetTradeByDetails);
        return ResponseEntity.ok(tradeService.getAllTradeByDetails(page, requestForGetTradeByDetails));
    }

    @GetMapping("/trade/{transactionId}")
    public ResponseEntity<TransferResponse> getTradeByTransactionId(@PathVariable @NonNull String transactionId) {
        return ResponseEntity.ok(tradeService.getTradeByTransactionId(transactionId));
    }
}
