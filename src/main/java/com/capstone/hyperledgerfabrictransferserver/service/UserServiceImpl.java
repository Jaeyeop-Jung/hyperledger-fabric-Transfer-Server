package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.*;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    @Override
    public User findUserByJwtToken(HttpServletRequest httpServletRequest) {

        String token = null;
        if(httpServletRequest.getHeader("Authorization") != null && httpServletRequest.getHeader("Authorization").startsWith("Bearer ")){
            token = httpServletRequest.getHeader("Authorization").split(" ")[1];
        }

        if(token == null){
            throw new EmptyTokenException("Authorization 헤더가 비어있거나 잘못되었습니다");
        }

        if(!jwtTokenProvider.validateToken(token)){
            throw new IncorrectTokenException("잘못된 토큰으로 요청했습니다");
        }

        return userRepository.findById(jwtTokenProvider.findUserIdByJwt(token))
                .orElseThrow(() -> new DeletedUserException("삭제되거나 존재하지 않는 유저입니다"));
    }

    @Override
    public UserLoginResponse join(UserJoinRequest userJoinRequest) {

        if(userRepository.existsByStudentId(userJoinRequest.getStudentId())){
            throw new AlreadyExistUserException("학번 : " + userJoinRequest.getStudentId() + " 는 이미 가입된 학번입니다");
        }

        User savedUser = User.of(
                userJoinRequest.getStudentId(),
                bCryptPasswordEncoder.encode(userJoinRequest.getPassword()),
                UserRole.ROLE_USER,
                userJoinRequest.getName()
        );

        userRepository.save(savedUser);

        return UserLoginResponse.builder()
                .accessToken("Bearer " + jwtTokenProvider.generateJwtToken(savedUser))
                .build();
    }

    @Override
    public UserLoginResponse login(HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest) {

        User findUser = userRepository.findByStudentId(userLoginRequest.getStudentId())
                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다")); // 예외처리

        if(!bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), findUser.getPassword())){
            throw new IncorrectPasswordException("잘못된 비밀번호 입니다");
        }

        return UserLoginResponse.builder()
                .accessToken("Bearer " + jwtTokenProvider.generateJwtToken(findUser))
                .build();
    }

    @Override
    public void changePassword(HttpServletRequest httpServletRequest, String newPassword) {

        User findUser = findUserByJwtToken(httpServletRequest);

        findUser.changePassword(bCryptPasswordEncoder.encode(newPassword));
    }
}
