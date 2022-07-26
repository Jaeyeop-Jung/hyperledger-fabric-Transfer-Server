package com.capstone.hyperledgerfabrictransferserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StoreImageTest {

    @Test
    @DisplayName("StoreImage 객체 생성 테스트")
    public void StoreImage_객체_생성_을_테스트한다(){
        // given

        // when
        StoreImage storeImage = StoreImage.of(
                UUID.randomUUID().toString(),
                100L,
                ImageFileExtension.JPG
        );

        // then
        assertThat(storeImage.getImageFileExtension()).isEqualTo(ImageFileExtension.JPG);
    }

}
