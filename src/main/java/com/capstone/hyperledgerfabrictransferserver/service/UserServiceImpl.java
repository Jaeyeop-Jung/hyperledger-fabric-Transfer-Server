package com.capstone.hyperledgerfabrictransferserver.service;

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

        if(token == null || !jwtTokenProvider.validateToken(token)){
            // 에외처리
        }

        return userRepository.findById(jwtTokenProvider.findUserIdByJwt(token))
                .orElseThrow();
    }

    @Override
    public UserLoginResponse join(UserJoinRequest userJoinRequest) {

        if(userRepository.existsByStudentId(userJoinRequest.getStudentId())){
            // 예외처리
        }
        if(userRepository.existsByName(userJoinRequest.getName())){
            // 예외처리
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
                .orElseThrow(); // 예외처리

        if(!bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), findUser.getPassword())){
            //예외처리
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
