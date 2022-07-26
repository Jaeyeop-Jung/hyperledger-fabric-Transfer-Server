package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.user.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * methodName : join
     * author : Jaeyeop Jung
     * description : 사용자(학생) 회원가입
     *
     * @param userJoinRequest the user join request
     * @return Jwt토큰
     */
    @PostMapping("/user")
    public ResponseEntity<UserLoginResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        return ResponseEntity.ok(userService.join(userJoinRequest));
    }

    /**
     * methodName : login
     * author : Jaeyeop Jung
     * description : 사용자(학생) 로그인
     *
     * @param userLoginRequest   the user login request
     * @return the
     */
    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @ModelAttribute UserLoginRequest userLoginRequest
    )
    {
        return ResponseEntity.ok(userService.login(userLoginRequest));
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
    @DeleteMapping("/user")
    public ResponseEntity<Void> delete(HttpServletRequest httpServletRequest) {

        userService.delete(httpServletRequest);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/user")
    public ResponseEntity<AssetDto> getAsset(HttpServletRequest httpServletRequest) {

        AssetDto response = userService.getAsset(httpServletRequest);

        return ResponseEntity.ok(response);
    }
}
