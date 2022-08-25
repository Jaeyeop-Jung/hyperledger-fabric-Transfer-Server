package com.capstone.hyperledgerfabrictransferserver.api.admin;

import com.capstone.hyperledgerfabrictransferserver.dto.store.CreateStoreRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.store.DeleteStoreRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.storeimage.StoreImageModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adin")
public class AdminStoreController {

    private final StoreService storeService;

    @PostMapping( "/store")
    public ResponseEntity<Void> create(
            @RequestPart MultipartFile multipartFile,
            @RequestPart CreateStoreRequest createStoreRequest
    )
    {
        storeService.createStore(createStoreRequest, multipartFile);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/store")
    public ResponseEntity<Void> delete(@RequestBody DeleteStoreRequest deleteStoreRequest) {
        storeService.deleteStore(deleteStoreRequest);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/storeimage")
    public ResponseEntity<Void> modifyStoreImage(
            @RequestPart @Nullable MultipartFile multipartFile,
            @RequestPart StoreImageModifyRequest storeImageModifyRequest
    )
    {
        storeService.modifyStoreImageBy(storeImageModifyRequest, multipartFile);
        return ResponseEntity.ok(null);
    }
}
