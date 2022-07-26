package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.AlreadyExistsStoreException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsStoreImageException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotFoundStoreException;
import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import com.capstone.hyperledgerfabrictransferserver.domain.StoreImage;
import com.capstone.hyperledgerfabrictransferserver.dto.StoreCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.StoreDeleteRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.StoreModifyRequest;
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
        if (storeRepository.existsByNameAndPhoneNumber(storeCreateRequest.getStoreName(), storeCreateRequest.getPhoneNumber())) {
            throw new AlreadyExistsStoreException("이미 존재하는 스토어입니다");
        }

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

    @Transactional
    public void deleteStore(@NonNull StoreDeleteRequest storeDeleteRequest) {
        if (!storeRepository.existsByNameAndPhoneNumber(storeDeleteRequest.getName(), storeDeleteRequest.getPhoneNumber())) {
            throw new NotFoundStoreException("존재하지 않은 스토어입니다");
        }

        Store findStore = storeRepository.findByNameAndPhoneNumber(storeDeleteRequest.getName(), storeDeleteRequest.getPhoneNumber())
                .get();

        if (findStore.getStoreImage() == null) {
            throw new NotExistsStoreImageException("스토어의 이미지가 존재하지 않습니다");
        }

        storeImageService.deleteStoreImageBy(findStore.getStoreImage());
        storeRepository.delete(findStore);
    }

    @Transactional
    public void modifyStoreImageBy(StoreModifyRequest storeModifyRequest, MultipartFile multipartFile) {
        Store findStore = storeRepository.findByNameAndPhoneNumber(storeModifyRequest.getName(), storeModifyRequest.getPhoneNumber())
                .orElseThrow(() -> new NotExistsStoreImageException("존재하지 않은 스토어 이미지입니다"));
        storeImageService.modifyStoreImage(findStore.getStoreImage(), multipartFile);
    }

}
