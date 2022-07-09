package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.*;
import com.capstone.hyperledgerfabrictransferserver.service.TradeService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PagingTransferResponseDto> getAllTradeBy(
            int page,
            Long sender,
            Long receiver,
            LocalDateTime dateCreated
    ) {
        return ResponseEntity.ok(tradeService.getAllTradeBy(page, sender, receiver, dateCreated));
    }
}
