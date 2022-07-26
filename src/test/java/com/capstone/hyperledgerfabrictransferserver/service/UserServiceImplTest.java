package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.user.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserLoginResponse;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserService userService;

    @Mock
    FabricService fabricService;

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
        User user = User.of("20170001", "test", UserRole.ROLE_STUDENT, "test");

        when(jwtTokenProvider.findIdentifierByHttpServletRequest(any()))
                .thenReturn("20170001");
        when(userRepository.findByIdentifier(any()))
                .thenReturn(Optional.of(user));

        //when
        User findUser = userService.getUserByHttpServletRequest(httpServletRequest);

        //then
        verify(jwtTokenProvider).findIdentifierByHttpServletRequest(any());
        verify(userRepository).findByIdentifier(any());
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("유저 회원가입 테스트")
    void join_을_테스트한다() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .identifier("20170001")
                .password("test")
                .name("test")
                .build();
        Gateway gateway = mock(Gateway.class);

        when(userRepository.existsByIdentifier(userJoinRequest.getIdentifier()))
                .thenReturn(false);
        when(bCryptPasswordEncoder.encode(any()))
                .thenReturn("test");
        when(jwtTokenProvider.generateJwtToken(any()))
                .thenReturn("test");
        when(userRepository.save(any()))
                .thenReturn(User.of("20170001", "test", UserRole.ROLE_STUDENT, "test"));
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
                .identifier("20170001")
                .password("test")
                .build();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(userRepository.findByIdentifier(any()))
                .thenReturn(Optional.of(User.of("20170001", "test", UserRole.ROLE_STUDENT, "test")));
        when(bCryptPasswordEncoder.matches(any(), any()))
                .thenReturn(true);
        when(jwtTokenProvider.generateJwtToken(any()))
                .thenReturn("test");

        //when
        UserLoginResponse response = userService.login(userLoginRequest);

        //then
        verify(userRepository).findByIdentifier(any());
        verify(bCryptPasswordEncoder).matches(any(), any());
        assertThat(response.getAccessToken()).isEqualTo("Bearer test");
    }

    @Test
    @DisplayName("유저 비밀번호 변경 테스트")
    void changePassword_을_테스트한다() {

        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of("20170000", "test", UserRole.ROLE_STUDENT, "test");
        when(jwtTokenProvider.findIdentifierByHttpServletRequest(any()))
                .thenReturn("test");
        when(userRepository.findByIdentifier(any()))
                .thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(any()))
                .thenReturn("newTest");
        //when
        userService.changePassword(httpServletRequest, "newTest");

        //then
        verify(jwtTokenProvider).findIdentifierByHttpServletRequest(any());
        verify(userRepository).findByIdentifier(any());
        verify(bCryptPasswordEncoder).encode(any());
        assertThat(user.getPassword()).isEqualTo("newTest");

    }

    @Test
    @DisplayName("유저 삭제 테스트")
    public void delete_를_테스트한다() throws Exception {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of("20170000", "test", UserRole.ROLE_STUDENT, "test");
        Gateway gateway = mock(Gateway.class);

        when(jwtTokenProvider.findIdentifierByHttpServletRequest(any()))
                .thenReturn("20170000");
        when(userRepository.findByIdentifier(any()))
                .thenReturn(Optional.of(user));
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn("true");

        //when
        userService.delete(httpServletRequest);

        //then
        verify(userRepository).findByIdentifier(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
    }

    @Test
    @DisplayName("개인 자산 조회 테스트")
    public void getAsset_를_테스트한다() throws Exception {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = User.of("20170000", "test", UserRole.ROLE_STUDENT, "test");
        Gateway gateway = mock(Gateway.class);


        String test = objectMapper.writeValueAsString(
                AssetDto.builder()
                        .identifier("20170000")
                        .coin(new HashMap<>())
                        .owner("test")
                        .build()
        );

        when(jwtTokenProvider.findIdentifierByHttpServletRequest(any()))
                .thenReturn("test");
        when(userRepository.findByIdentifier(any()))
                .thenReturn(Optional.of(user));
        when(fabricService.getGateway())
                .thenReturn(gateway);
        when(fabricService.submitTransaction(any(), any(), any()))
                .thenReturn(test);

        //when
        userService.getAsset(httpServletRequest);

        //then
        verify(jwtTokenProvider).findIdentifierByHttpServletRequest(any());
        verify(userRepository).findByIdentifier(any());
        verify(fabricService).getGateway();
        verify(fabricService).submitTransaction(any(), any(), any());
        assertThat(user.getIdentifier()).isEqualTo("20170000");
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