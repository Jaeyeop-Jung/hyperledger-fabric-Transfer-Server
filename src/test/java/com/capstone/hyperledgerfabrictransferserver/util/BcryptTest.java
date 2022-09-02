package com.capstone.hyperledgerfabrictransferserver.util;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.assertj.core.api.Assertions.*;

public class BcryptTest {

    @Test
    @DisplayName("Bcrypt 해싱 테스트")
    void Bcrypt_hashpw_를_테스트한다() {
        // given
        String password = "test";

        // when
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // then
        assertThat(password).isNotEqualTo(hashPassword);
    }

    @Test
    @DisplayName("Bcrypt checkPw 테스트")
    void Bcrypt_checkPw_를_테스트한다() {
        // given
        String password = "test";
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // when
        boolean result = BCrypt.checkpw(password, hashPassword);

        // then
        assertThat(result).isTrue();
    }
}
