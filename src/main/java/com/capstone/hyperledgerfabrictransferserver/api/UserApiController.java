package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserApiController {

    public ResponseEntity<UserLoginResponse> join(UserJoinRequest userJoinRequest);

    public ResponseEntity<UserLoginResponse> login(HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest);

    public ResponseEntity.BodyBuilder changePassword(HttpServletRequest httpServletRequest, String newPassword);

}
