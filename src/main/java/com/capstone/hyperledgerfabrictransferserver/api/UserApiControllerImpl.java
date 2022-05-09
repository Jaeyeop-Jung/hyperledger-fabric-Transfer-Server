package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserApiControllerImpl implements UserApiController{

    private final UserService userService;

    @Override
    @PostMapping("/user")
    public ResponseEntity<UserLoginResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        return ResponseEntity.ok(userService.join(userJoinRequest));
    }

    @Override
    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            HttpServletRequest httpServletRequest,
            @ModelAttribute UserLoginRequest userLoginRequest
    )
    {
        return ResponseEntity.ok(userService.login(httpServletRequest, userLoginRequest));
    }

    @Override
    public ResponseEntity.BodyBuilder changePassword(HttpServletRequest httpServletRequest, String newPassword) {

        userService.changePassword(httpServletRequest, newPassword);
        return ResponseEntity.ok();
    }


}
