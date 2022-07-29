package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.*;
import com.capstone.hyperledgerfabrictransferserver.domain.*;
import com.capstone.hyperledgerfabrictransferserver.dto.user.*;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final FabricService fabricService;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public Long getNumberOfUserByUserRole(UserRole userRole) {
        return userRepository.countAllByUserRoleIs(userRole);
    }

    @Transactional(readOnly = true)
    public User getUserByIdentifier(@NonNull String identifier) {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IncorrectIdentifierException("잘못된 식별 번호로 요청했습니다"));
    }

    @Transactional(readOnly = true)
    public User getUserByHttpServletRequest(HttpServletRequest httpServletRequest) {
        String identifier = jwtTokenProvider.findIdentifierByHttpServletRequest(httpServletRequest);

        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IncorrectIdentifierException("잘못된 식별 번호로 요청했습니다"));
    }

    /**
     * methodName : join
     * author : Jaeyeop Jung
     * description : User 엔티티를 저장하는 메서드
     *
     * @param userJoinRequest the user join request
     * @return the
     */
    @Transactional
    public UserLoginResponse join(UserJoinRequest userJoinRequest) {

        if (userRepository.existsByIdentifier(userJoinRequest.getIdentifier())) {
            throw new AlreadyExistUserException("이미 존재하는 식별 번호입니다");
        }

        User savedUser = userRepository.save(
                User.of(
                        userJoinRequest.getIdentifier(),
                        bCryptPasswordEncoder.encode(userJoinRequest.getPassword()),
                        userJoinRequest.getUserRole(),
                        userJoinRequest.getName()
                )
        );

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "CreateAsset", "asset" + savedUser.getId(), savedUser.getIdentifier(), savedUser.getName(), savedUser.getUserRole().name());
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
    @Transactional(readOnly = true)
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {

        User findUser = userRepository.findByIdentifier(userLoginRequest.getIdentifier())
                .orElseThrow(() -> new IncorrectIdentifierException("가입하지 않거나 잘못된 식별 번호입니다"));

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
    @Transactional
    public void changePassword(HttpServletRequest httpServletRequest, String newPassword) {

        User findUser = getUserByHttpServletRequest(httpServletRequest);

        findUser.changePassword(bCryptPasswordEncoder.encode(newPassword));
    }

    /**
     * methodName : delete
     * author : Jaeyeop Jung
     * description : User 엔티티를 삭제하는 메서드
     *
     * @param httpServletRequest the http servlet request
     */
    @Transactional
    public void delete(HttpServletRequest httpServletRequest) {

        User findUser = getUserByHttpServletRequest(httpServletRequest);
        userRepository.delete(findUser);

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "DeleteAsset", "asset" + findUser.getId());
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("DeleteAsset 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Transactional(readOnly = true)
    public AssetDto getAsset(HttpServletRequest httpServletRequest) {

        User findUser = getUserByHttpServletRequest(httpServletRequest);

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

    @Transactional(readOnly = true)
    public PagingUserDto getAllUser(int page) {
        Page<User> findAllUser = userRepository.findAll(PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));
        return PagingUserDto.from(findAllUser);
    }
}
