package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

public interface UserApiController {

    public ResponseEntity<UserLoginResponse> join(UserJoinRequest userJoinRequest);

    public ResponseEntity<UserLoginResponse> login(HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest);

}
