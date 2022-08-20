package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.admin.AdminLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.admin.AdminLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.coin.DailyCoinTradingVolume;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.PagingTradeResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.RequestForGetTradeByDetails;
import com.capstone.hyperledgerfabrictransferserver.dto.trade.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.user.*;
import com.capstone.hyperledgerfabrictransferserver.service.AdminService;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final AdminService adminService;
    private final UserService userService;
    private final TradeService tradeService;

    @GetMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
        @ModelAttribute AdminLoginRequest adminLoginRequest
    ){
        return ResponseEntity.ok(adminService.login(adminLoginRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<PagingUserDto> getAllUser(
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(userService.getAllUser(page));
    }

    @PutMapping("/user")
    public ResponseEntity<Void> modifyUserInfo(@RequestBody UserModifyRequest userModifyRequest) {
        userService.modifyUserInfo(userModifyRequest);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> forceDeleteUser(@RequestBody ForceDeleteUserRequest forceDeleteUserRequest) {
        userService.delete(forceDeleteUserRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/trade")
    public ResponseEntity<PagingTradeResponseDto> getAllTradeByDetails(
            @RequestParam(defaultValue = "1") int page,
            @ModelAttribute RequestForGetTradeByDetails requestForGetTradeByDetails
    ) {
        System.out.println("requestForGetTradeByDetails = " + requestForGetTradeByDetails.getSenderIdentifier());
        System.out.println("requestForGetTradeByDetails = " + requestForGetTradeByDetails.getReceiverIdentifier());
        System.out.println("requestForGetTradeByDetails = " + requestForGetTradeByDetails.getSenderUserRole());
        System.out.println("requestForGetTradeByDetails = " + requestForGetTradeByDetails.getReceiverUserRole());
        return ResponseEntity.ok(tradeService.getAllTradeByDetails(page, requestForGetTradeByDetails));
    }

    @GetMapping("/trade/{transactionId}")
    public ResponseEntity<TransferResponse> getTradeByTransactionId(@PathVariable @NonNull String transactionId) {
        return ResponseEntity.ok(tradeService.getTradeByTransactionId(transactionId));
    }

    @GetMapping("/trades/coin")
    public ResponseEntity<List<DailyCoinTradingVolume>> getDailyCoinTradingVolume(
            @RequestParam String coinName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromLocalDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toLocalDateTime
            ) {
        return ResponseEntity.ok(tradeService.getDailyCoinTradingVolume(coinName, fromLocalDateTime, toLocalDateTime));
    }
}
