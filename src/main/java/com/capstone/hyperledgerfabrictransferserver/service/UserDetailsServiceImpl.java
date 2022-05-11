package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.DeletedUserException;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserDetailsImpl;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * methodName : loadUserByUsername
     * author : Jaeyeop Jung
     * description : Authentication 객체를 만들기 위해 UserRepository를 통해 토큰에 해당하는
     *               User 도메인 객체를 가져와 UserDetailsImpl을 반환
     *
     * @param id the id
     * @return the user by username
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User findUser = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new DeletedUserException("삭제되거나 존재하지 않는 유저입니다"));

        if(findUser != null) {
            return UserDetailsImpl.of(
                    findUser.getId(),
                    findUser.getStudentId(),
                    findUser.getPassword(),
                    findUser.getUserRole(),
                    findUser.getName()
            );
        }

        return null;
    }
}
