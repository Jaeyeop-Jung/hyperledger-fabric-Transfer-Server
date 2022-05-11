package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.*;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.util.CustomFabricGateway;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    /**
     * methodName : getUserByJwtToken
     * author : Jaeyeop Jung
     * description : HttpServletRequest에서 User 엔티티를 가져오는 메서드
     *
     * @param httpServletRequest the http servlet request
     * @return the user by jwt token
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserByJwtToken(HttpServletRequest httpServletRequest) {

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

    /**
     * methodName : join
     * author : Jaeyeop Jung
     * description : User 엔티티를 저장하는 메서드
     *
     * @param userJoinRequest the user join request
     * @return the
     */
    @Override
    @Transactional
    public UserLoginResponse join(UserJoinRequest userJoinRequest) {

        if(userRepository.existsByStudentId(userJoinRequest.getStudentId())){
            throw new AlreadyExistUserException("학번 : " + userJoinRequest.getStudentId() + " 는 이미 가입된 학번입니다");
        }

        User savedUser = userRepository.save(
                User.of(
                        userJoinRequest.getStudentId(),
                        bCryptPasswordEncoder.encode(userJoinRequest.getPassword()),
                        UserRole.ROLE_USER,
                        userJoinRequest.getName()
                )
        );

        byte[] fabricResponse;
        try {
            Contract contract = CustomFabricGateway.getContract();
            fabricResponse = contract.submitTransaction("CreateAsset", "asset" + savedUser.getId(), userJoinRequest.getName());
        } catch (Exception e){
            throw new IncorrectContractException("CreateAsset 체인코드 실행 중 오류가 발생했습니다");
        }

        System.out.println(new String(fabricResponse, StandardCharsets.UTF_8));

        return UserLoginResponse.builder()
                .accessToken("Bearer " + jwtTokenProvider.generateJwtToken(savedUser))
                .build();
    }

    /**
     * methodName : login
     * author : Jaeyeop Jung
     * description : User를 통해 Jwt 토큰을 발행하여 로그인 기능을 담당하는 메서드
     *
     * @param httpServletRequest the http servlet request
     * @param userLoginRequest   the user login request
     * @return the
     */
    @Override
    @Transactional(readOnly = true)
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

    /**
     * methodName : changePassword
     * author : Jaeyeop Jung
     * description : User 엔티티의 password를 변경하는 메서드
     *
     * @param httpServletRequest the http servlet request
     * @param newPassword        the new password
     */
    @Override
    @Transactional
    public void changePassword(HttpServletRequest httpServletRequest, String newPassword) {

        User findUser = getUserByJwtToken(httpServletRequest);

        findUser.changePassword(bCryptPasswordEncoder.encode(newPassword));
    }

    /**
     * methodName : delete
     * author : Jaeyeop Jung
     * description : User 엔티티를 삭제하는 메서드
     *
     * @param httpServletRequest the http servlet request
     */
    @Override
    @Transactional
    public void delete(HttpServletRequest httpServletRequest) {

        User findUser = getUserByJwtToken(httpServletRequest);

        userRepository.delete(findUser);
    }
}
