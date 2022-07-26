package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import com.capstone.hyperledgerfabrictransferserver.domain.StoreImage;
import com.capstone.hyperledgerfabrictransferserver.dto.StoreCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.StoreRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreImageService storeImageService;

    @Transactional
    public void createStore(@NonNull StoreCreateRequest storeCreateRequest, MultipartFile multipartFile) {
        StoreImage storeImage = null;
        if (!multipartFile.isEmpty()) {
            storeImage = storeImageService.createStoreImageBy(multipartFile);
        }

        storeRepository.save(
                Store.of(
                        storeCreateRequest.getStoreName(),
                        storeCreateRequest.getPhoneNumber(),
                        storeCreateRequest.getAddress(),
                        storeImage
                )
        );
    }

}
