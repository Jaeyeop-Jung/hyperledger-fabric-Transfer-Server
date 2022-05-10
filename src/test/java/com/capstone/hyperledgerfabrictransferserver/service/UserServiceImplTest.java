package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    void findUserByJwtToken() {
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
    void login() {
    }

    @Test
    void changePassword() {
    }
}