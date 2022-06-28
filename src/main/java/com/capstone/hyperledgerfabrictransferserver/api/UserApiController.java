package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public interface UserApiController {

    public ResponseEntity<UserLoginResponse> join(UserJoinRequest userJoinRequest);

    public ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest);

    public ResponseEntity<Void> changePassword(HttpServletRequest httpServletRequest, String newPassword);

    public ResponseEntity<Void> delete(HttpServletRequest httpServletRequest);

    public ResponseEntity<AssetDto> getAsset(HttpServletRequest httpServletRequest);
}
