package com.capstone.hyperledgerfabrictransferserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StoreTest {

    @Test
    @DisplayName("Store 객체 생성 테스트")
    void Store_객체_생성_을_테스트한다() {

        // given

        // when
        Store store1 = Store.of(
                "test",
                "01000000000",
                "test"
        );

        Store store2 = Store.of(
                "test2",
                "01000000001",
                "test2"
        );

        // then
        assertThat(store1.getName()).isEqualTo("test");
        assertThat(store2.getName()).isEqualTo("test2");
        assertThat(store1).isNotSameAs(store2);
    }


}
