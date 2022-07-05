package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.*;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.*;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final FabricService fabricService;
    private final ObjectMapper objectMapper;

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

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "CreateAsset", "asset" + savedUser.getId(), String.valueOf(savedUser.getStudentId()), userJoinRequest.getName());
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("CreateAsset 체인코드 실행 중 오류가 발생했습니다");
        }

        return UserLoginResponse.builder()
                .accessToken("Bearer " + jwtTokenProvider.generateJwtToken(savedUser))
                .build();
    }

    /**
     * methodName : login
     * author : Jaeyeop Jung
     * description : User를 통해 Jwt 토큰을 발행하여 로그인 기능을 담당하는 메서드
     *
     * @param userLoginRequest   the user login request
     * @return the
     */
    @Override
    @Transactional(readOnly = true)
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {

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
        try {
            Gateway gateway = fabricService.getGateway();
            boolean response = Boolean.valueOf(fabricService.submitTransaction(gateway, "DeleteAsset", "asset" + findUser.getId()));
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("DeleteAsset 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AssetDto getAsset(HttpServletRequest httpServletRequest) {

        User findUser = getUserByJwtToken(httpServletRequest);

        try {
            Gateway gateway = fabricService.getGateway();
            String response = fabricService.submitTransaction(
                    gateway, "GetAsset", "asset" + findUser.getId()
            );
            fabricService.close(gateway);
            return objectMapper.readValue(response, AssetDto.class);
        } catch (Exception e){
            throw new IncorrectContractException("GetAsset 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PagingUserDto getAllUser(int page) {

        Page<User> findAllUser = userRepository.findAll(PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));

        return PagingUserDto.builder()
                .userDtoList(findAllUser.getContent())
                .totalUserNumber(findAllUser.getTotalElements())
                .totalPage(findAllUser.getTotalPages())
                .build();
    }
}
