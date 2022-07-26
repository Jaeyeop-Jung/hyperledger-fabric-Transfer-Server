package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.ImageFileExtension;
import com.capstone.hyperledgerfabrictransferserver.domain.StoreImage;
import com.capstone.hyperledgerfabrictransferserver.repository.StoreImageRepository;
import com.capstone.hyperledgerfabrictransferserver.util.ImageFileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreImageServiceTest {

    @InjectMocks
    StoreImageService storeImageService;

    @Mock
    StoreImageRepository storeImageRepository;

    @Spy
    ImageFileUtil imageFileUtil;


}
