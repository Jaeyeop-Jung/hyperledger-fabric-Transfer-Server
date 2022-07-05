package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.*;
import com.capstone.hyperledgerfabrictransferserver.service.CoinService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiControllerImpl implements AdminApiController{

    private final UserService userService;

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
}
