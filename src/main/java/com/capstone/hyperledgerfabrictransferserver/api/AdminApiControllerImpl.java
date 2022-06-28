package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiControllerImpl {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
        @ModelAttribute UserLoginRequest userLoginRequest
    ){
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }
}
