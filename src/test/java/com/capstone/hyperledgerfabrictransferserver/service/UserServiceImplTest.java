package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.*;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.gateway.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    FabricServiceImpl fabricService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Spy
    ObjectMapper objectMapper;

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
    void join_을_테스트한다() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .studentId(20170000L)
                .password("test")
                .name("test")
                .build();
        Gateway gateway = mock(Gateway.class);

        when(userRepository.existsByStudentId(userJoinRequest.getStudentId()))
                .thenReturn(false);
        when(bCryptPasswordEncoder.encode(any()))
                .thenReturn("test");
        when(jwtTokenProvider.generateJwtToken(any()))
                .thenReturn("test");
        when(userRepository.save(any()))
                .thenReturn(User.of(20170000L, "test", UserRole.ROLE_USER, "test"));
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(null);

        //when
        UserLoginResponse response = userService.join(userJoinRequest);

        //then
        verify(userRepository).save(any());
        verify(bCryptPasswordEncoder).encode(any());
        verify(jwtTokenProvider).generateJwtToken(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
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
        UserLoginResponse response = userService.login(userLoginRequest);

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

    @Test
    @DisplayName("유저 삭제 테스트")
    public void delete_를_테스트한다() throws Exception {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        Gateway gateway = mock(Gateway.class);

        when(httpServletRequest.getHeader(any()))
                .thenReturn("Bearer test");
        when(jwtTokenProvider.validateToken(any()))
                .thenReturn(true);
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn("true");

        //when
        userService.delete(httpServletRequest);

        //then
        verify(httpServletRequest, times(3)).getHeader(any());
        verify(jwtTokenProvider).validateToken(any());
        verify(userRepository).findById(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
    }

    @Test
    @DisplayName("개인 자산 조회 테스트")
    public void getAsset_를_테스트한다() throws Exception {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        Gateway gateway = mock(Gateway.class);


        String test = objectMapper.writeValueAsString(
                AssetDto.builder()
                        .studentId(20170000L)
                        .coin(new HashMap<>())
                        .owner("test")
                        .sender(null)
                        .receiver(null)
                        .amount(null)
                        .build()
        );

        when(httpServletRequest.getHeader(any()))
                .thenReturn("Bearer test");
        when(jwtTokenProvider.validateToken(any()))
                .thenReturn(true);
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(test);

        //when
        userService.getAsset(httpServletRequest);

        //then
        verify(httpServletRequest, times(3)).getHeader(any());
        verify(jwtTokenProvider).validateToken(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
        assertThat(user.getStudentId()).isEqualTo(20170000L);
    }


//    !!!! 이건 통합테스트로 빼야겠다
//    @Test
//    @DisplayName("모든 유저 조회 테스트")
//    public void getAllUser_를_테스트한다() throws Exception {
//        // given
//        ArrayList<User> userList = new ArrayList<>();
//        for (int i = 0; i < 21; i++) {
//            User user = User.of(
//                    Long.valueOf(i),
//                    "test",
//                    UserRole.ROLE_USER,
//                    "test" + i
//            );
//            userList.add(user);
//        }
//
//        when(userRepository.findAll((PageRequest.of(anyInt(), anyInt(), Sort.Direction.DESC, anyString()))))
//                .thenReturn(new PageImpl<>(userList));
//
//        // when
//        PagingUserDto response = userService.getAllUser(1);
//
//        // then
//        verify(userService).getAllUser(any());
//        assertThat(response.getTotalPage()).isEqualTo(2);
//    }

}