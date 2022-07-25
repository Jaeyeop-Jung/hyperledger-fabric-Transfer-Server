package com.capstone.hyperledgerfabrictransferserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StoreTest {

    @Test
    @DisplayName("Store 객체 생성 테스트")
    void Store_객체_생성_을_테스트한다() {

        // given

        // when
        Store store = Store.of(
                "test",
                "01000000000",
                "test"
        );

        // then
        Assertions.assertThat(store.getName()).isEqualTo("test");
    }

}
