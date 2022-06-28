package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.DeletedUserException;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 저장 테스트")
    void save_를_테스트한다(){
        //given
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("회원 찾기 테스트")
    public void findById_를_테스트한다() throws Exception {
        //given
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        User savedUser = userRepository.save(user);

        //when
        User findUser = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new DeletedUserException(""));

        //then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("StudentId로 회원 존재 테스트")
    void existsByStudentId_을_테스트한다() {
        //given
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        userRepository.save(user);

        //when
        boolean response = userRepository.existsByStudentId(20170000L);

        //then
        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("Name으로 회원 존재 테스트")
    void existsByName_을_테스트한다() {
        //given
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        userRepository.save(user);

        //when
        boolean response = userRepository.existsByName("test");

        //then
        assertThat(response).isTrue();
    }

    @Test
    void findByStudentId() {
        //given
        User user = User.of(20170000L, "test", UserRole.ROLE_USER, "test");
        userRepository.save(user);

        //when
        User findUser = userRepository.findByStudentId(20170000L)
                .orElseThrow(() -> new DeletedUserException(""));

        //then
        assertThat(user).isEqualTo(findUser);
    }
}