package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserApiControllerImpl implements UserApiController{

    private final UserService userService;

    /**
     * methodName : join
     * author : Jaeyeop Jung
     * description : 사용자(학생) 회원가입
     *
     * @param userJoinRequest the user join request
     * @return Jwt토큰
     */
    @Override
    @PostMapping("/user")
    public ResponseEntity<UserLoginResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        return ResponseEntity.ok(userService.join(userJoinRequest));
    }

    /**
     * methodName : login
     * author : Jaeyeop Jung
     * description : 사용자(학생) 로그인
     *
     * @param httpServletRequest the http servlet request
     * @param userLoginRequest   the user login request
     * @return the
     */
    @Override
    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            HttpServletRequest httpServletRequest,
            @ModelAttribute UserLoginRequest userLoginRequest
    )
    {
        return ResponseEntity.ok(userService.login(httpServletRequest, userLoginRequest));
    }

    /**
     * methodName : changePassword
     * author : Jaeyeop Jung
     * description : 사용자(학생) 비밀번호 변경
     *
     * @param httpServletRequest the http servlet request
     * @param newPassword        the new password
     * @return the password
     */
    @Override
    @PatchMapping("/user")
    public ResponseEntity<Void> changePassword(HttpServletRequest httpServletRequest, String newPassword) {

        userService.changePassword(httpServletRequest, newPassword);

        return ResponseEntity.ok(null);
    }

    /**
     * methodName : delete
     * author : Jaeyeop Jung
     * description : 사용자(학생) 회원 정보 삭제
     *
     * @param httpServletRequest the http servlet request
     * @return the
     */
    @Override
    @DeleteMapping("/user")
    public ResponseEntity<Void> delete(HttpServletRequest httpServletRequest) {

        userService.delete(httpServletRequest);

        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping("/userasset")
    public ResponseEntity<String> checkAsset(HttpServletRequest httpServletRequest) {

        String response = userService.checkAsset(httpServletRequest);

        return ResponseEntity.ok(response);
    }
}
