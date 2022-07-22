package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.DeletedUserException;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

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
        User user = User.of("20170001", "test", UserRole.ROLE_STUDENT, "test");

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("회원 찾기 테스트")
    public void findById_를_테스트한다() throws Exception {
        //given
        User user = User.of("20170001", "test", UserRole.ROLE_STUDENT, "test");
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
        User user = User.of("20170001", "test", UserRole.ROLE_STUDENT, "test");
        userRepository.save(user);

        //when
        boolean response = userRepository.existsByIdentifier("20170001");

        //then
        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("Name으로 회원 존재 테스트")
    void existsByName_을_테스트한다() {
        //given
        User user = User.of("20170001", "test", UserRole.ROLE_STUDENT, "test");
        userRepository.save(user);

        //when
        boolean response = userRepository.existsByName("test");

        //then
        assertThat(response).isTrue();
    }

    @Test
    void findByStudentId() {
        //given
        User user = User.of("20170001", "test", UserRole.ROLE_STUDENT, "test");
        userRepository.save(user);

        //when
        User findUser = userRepository.findByIdentifier("20170001")
                .orElseThrow(() -> new DeletedUserException(""));

        //then
        assertThat(user).isEqualTo(findUser);
    }
}