package com.capstone.hyperledgerfabrictransferserver.util;

import com.capstone.hyperledgerfabrictransferserver.domain.ImageFileExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ImageFileUtilTest {

    @Test
    @DisplayName("파일 확장자 얻기 테스트")
    void 이미지_파일_확장자_얻기_를_테스트한다() throws Exception {
        // give
        String fileName = "test.1.2.test";

        // when
        int start = fileName.lastIndexOf(".") + 1;
        int end = fileName.length();

        String extension = fileName.substring(start, end);

        // then
        assertThat(extension).isEqualTo("test");
    }

    @Test
    @DisplayName("이미지 파일 확장자 검증 테스트")
    void 이미지_파일_확장자_검증_을_테스트한다() throws Exception{
        // given
        String enableTestExample[] = {"test.jpg", "test.jpeg", "test.png", "test.PNG"};
        String unableTestExample[] = {"test.good", "test.xlsx"};
        List<String> enableExtension = Stream.of(ImageFileExtension.values())
                .map(extension -> extension.name())
                .collect(Collectors.toList());
        List<Boolean> enableVerifyList = new ArrayList<>();
        List<Boolean> unableVerifyList = new ArrayList<>();

        // when
        for (String example : enableTestExample) {
            int start = example.lastIndexOf(".") + 1;
            int end = example.length();
            boolean isEnable = enableExtension.contains(example.substring(start, end).toLowerCase()) || enableExtension.contains(example.substring(start, end).toUpperCase());
            enableVerifyList.add(isEnable);
        }
        for (String example : unableTestExample) {
            int start = example.lastIndexOf(".") + 1;
            int end = example.length();
            boolean isEnable = enableExtension.contains(example.substring(start, end).toLowerCase()) || enableExtension.contains(example.substring(start, end).toUpperCase());
            unableVerifyList.add(isEnable);
        }

        // then
        for (Boolean result : enableVerifyList) {
            assertThat(result).isTrue();
        }
        for (Boolean result : unableVerifyList) {
            assertThat(result).isFalse();
        }

    }

    @Test
    @DisplayName("이미지 파일 저장 테스트")
    void 이미지_파일_저장_을_테스트한다() throws Exception{
        // given
        String imageFilePath = System.getenv("IMAGE_FILE_PATH");
        ClassPathResource resource = new ClassPathResource("testimage.png");
        File writtenFile = new File(imageFilePath + UUID.randomUUID() + "." + ImageFileExtension.PNG);
        FileInputStream fileInputStream = new FileInputStream(resource.getFile());
        FileOutputStream fileOutputStream = new FileOutputStream(writtenFile);
        MockMultipartFile multipartFile = new MockMultipartFile("testimage.png", fileInputStream);
        
        // when
        byte[] bytes = multipartFile.getBytes();
        fileOutputStream.write(bytes);
        fileInputStream.close();
        fileOutputStream.close();

        //then
        assertThat(writtenFile.exists()).isTrue();
        assertThat(writtenFile.isFile()).isTrue();
        assertThat(writtenFile.length()).isEqualTo(resource.getFile().length());
    }

}
