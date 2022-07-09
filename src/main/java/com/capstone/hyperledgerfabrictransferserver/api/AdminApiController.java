package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.*;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminApiController {

    public ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest);

    public ResponseEntity<PagingUserDto> getAllUser(int page);

    public ResponseEntity<PagingTransferResponseDto> getAllTradeBy(int page, Long sender, Long receiver, LocalDateTime dateCreated);
}
