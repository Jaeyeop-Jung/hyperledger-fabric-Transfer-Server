package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.service.CoinService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiControllerImpl implements AdminApiController{

    private final UserService userService;
    private final CoinService coinService;

    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
        @ModelAttribute UserLoginRequest userLoginRequest
    ){
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }


}
