package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("Jwt토큰으로 유저 찾기 테스트")
    void findUserByJwtToken_을_테스트한다() {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");

        when(httpServletRequest.getHeader(any()))
                .thenReturn("Bearer test");
        when(jwtTokenProvider.validateToken(any()))
                .thenReturn(true);
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        //when
        User findUser = userService.getUserByJwtToken(httpServletRequest);

        //then
        verify(httpServletRequest, times(3)).getHeader(any());
        verify(jwtTokenProvider).validateToken(any());
        verify(userRepository).findById(any());
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("유저 회원가입 테스트")
    void join_을_테스트한다() {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .studentId(20170000L)
                .password("test")
                .name("test")
                .build();

        when(userRepository.existsByStudentId(userJoinRequest.getStudentId()))
                .thenReturn(false);
        when(bCryptPasswordEncoder.encode(any()))
                .thenReturn("test");
        when(jwtTokenProvider.generateJwtToken(any()))
                .thenReturn("test");

        //when
        UserLoginResponse response = userService.join(userJoinRequest);

        //then
        verify(userRepository).save(any());
        verify(bCryptPasswordEncoder).encode(any());
        verify(jwtTokenProvider).generateJwtToken(any());
        assertThat(response.getAccessToken()).isEqualTo("Bearer test");
    }

    @Test
    @DisplayName("유저 로그인 테스트")
    void login_을_테스트한다() {

        //given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .studentId(20170000L)
                .password("test")
                .build();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(userRepository.findByStudentId(any()))
                .thenReturn(Optional.of(User.of(20170000L, "test", UserRole.ROLE_USER, "test")));
        when(bCryptPasswordEncoder.matches(any(), any()))
                .thenReturn(true);
        when(jwtTokenProvider.generateJwtToken(any()))
                .thenReturn("test");

        //when
        UserLoginResponse response = userService.login(httpServletRequest, userLoginRequest);

        //then
        verify(userRepository).findByStudentId(any());
        verify(bCryptPasswordEncoder).matches(any(), any());
        assertThat(response.getAccessToken()).isEqualTo("Bearer test");
    }

    @Test
    @DisplayName("유저 비밀번호 변경 테스트")
    void changePassword_을_테스트한다() {

        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        when(httpServletRequest.getHeader(any()))
                .thenReturn("Bearer test");
        when(jwtTokenProvider.validateToken(any()))
                .thenReturn(true);
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(any()))
                .thenReturn("newTest");
        //when
        userService.changePassword(httpServletRequest, "newTest");

        //then
        verify(httpServletRequest, times(3)).getHeader(any());
        verify(jwtTokenProvider).validateToken(any());
        verify(userRepository).findById(any());
        verify(bCryptPasswordEncoder).encode(any());
        assertThat(user.getPassword()).isEqualTo("newTest");

    }

}