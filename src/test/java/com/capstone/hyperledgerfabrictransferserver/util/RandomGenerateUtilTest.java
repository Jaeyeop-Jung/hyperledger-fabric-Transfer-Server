package com.capstone.hyperledgerfabrictransferserver.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RandomGenerateUtilTest {

    @Test
    void generate() {
        // given
        int length = 10;

        // when
        String generate = RandomGenerateUtil.generate(length);

        // then
        assertThat(generate.length()).isEqualTo(10);
    }
}