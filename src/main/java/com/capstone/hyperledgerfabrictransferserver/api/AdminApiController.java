package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface AdminApiController {

    public ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest);

    public ResponseEntity<PagingUserDto> getAllUser(int page);
}
