package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.AlreadyExistsStoreException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsStoreImageException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotFoundStoreException;
import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import com.capstone.hyperledgerfabrictransferserver.domain.StoreImage;
import com.capstone.hyperledgerfabrictransferserver.dto.store.CreateStoreRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.store.DeleteStoreRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.store.GetStoreResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.store.PagingStoreDto;
import com.capstone.hyperledgerfabrictransferserver.dto.storeimage.StoreImageModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.StoreRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreImageService storeImageService;


    @Transactional(readOnly = true)
    public GetStoreResponse getStore(@NonNull String name, @NonNull String phoneNumber) {
        Store findStore = storeRepository.findByNameAndPhoneNumber(name, phoneNumber)
                .orElseThrow(() -> new NotFoundStoreException("스토어를 찾지 못했습니다"));
        return GetStoreResponse.from(findStore);
    }

    @Transactional(readOnly = true)
    public PagingStoreDto getAllStore(@NonNull int page) {
        Page<Store> findStore = storeRepository.findAllBy(PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));
        return PagingStoreDto.from(findStore);
    }

    @Transactional
    public void createStore(@NonNull CreateStoreRequest createStoreRequest, MultipartFile multipartFile) {
        if (storeRepository.existsByNameAndPhoneNumber(createStoreRequest.getStoreName(), createStoreRequest.getPhoneNumber())) {
            throw new AlreadyExistsStoreException("이미 존재하는 스토어입니다");
        }

        StoreImage storeImage = null;
        if (!multipartFile.isEmpty()) {
            storeImage = storeImageService.createStoreImageBy(multipartFile);
        }

        storeRepository.save(
                Store.of(
                        createStoreRequest.getStoreName(),
                        createStoreRequest.getPhoneNumber(),
                        createStoreRequest.getAddress(),
                        storeImage
                )
        );
    }

    @Transactional
    public void deleteStore(@NonNull DeleteStoreRequest deleteStoreRequest) {
        if (!storeRepository.existsByNameAndPhoneNumber(deleteStoreRequest.getName(), deleteStoreRequest.getPhoneNumber())) {
            throw new NotFoundStoreException("존재하지 않은 스토어입니다");
        }

        Store findStore = storeRepository.findByNameAndPhoneNumber(deleteStoreRequest.getName(), deleteStoreRequest.getPhoneNumber())
                .get();

        if (findStore.getStoreImage() == null) {
            throw new NotExistsStoreImageException("스토어의 이미지가 존재하지 않습니다");
        }

        storeImageService.deleteStoreImageBy(findStore.getStoreImage());
        storeRepository.delete(findStore);
    }

    @Transactional
    public void modifyStoreImageBy(StoreImageModifyRequest storeImageModifyRequest, MultipartFile multipartFile) {
        Store findStore = storeRepository.findByNameAndPhoneNumber(storeImageModifyRequest.getName(), storeImageModifyRequest.getPhoneNumber())
                .orElseThrow(() -> new NotExistsStoreImageException("존재하지 않은 스토어 이미지입니다"));
        storeImageService.modifyStoreImage(findStore.getStoreImage(), multipartFile);
    }

}
