package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.FailToWriteImageFileException;
import com.capstone.hyperledgerfabrictransferserver.domain.ImageFileExtension;
import com.capstone.hyperledgerfabrictransferserver.domain.StoreImage;
import com.capstone.hyperledgerfabrictransferserver.repository.StoreImageRepository;
import com.capstone.hyperledgerfabrictransferserver.util.ImageFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreImageService {

    private final StoreImageRepository storeImageRepository;
    private final ImageFileUtil imageFileUtil;

    @Transactional
    public StoreImage createStoreImageBy(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();
        try {
            imageFileUtil.saveImageFileBy(multipartFile, uuid);
        } catch (IOException e) {
            throw new FailToWriteImageFileException("이미지 파일 저장에 실패했습니다");
        }

        return storeImageRepository.save(
                StoreImage.of(
                        uuid + imageFileUtil.getFileExtension(multipartFile.getOriginalFilename()),
                        multipartFile.getSize(),
                        ImageFileExtension.valueOf(imageFileUtil.getFileExtension(multipartFile.getOriginalFilename()))
                )
        );
    }
}
