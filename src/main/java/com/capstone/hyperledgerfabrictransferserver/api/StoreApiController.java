package com.capstone.hyperledgerfabrictransferserver.api;


import com.capstone.hyperledgerfabrictransferserver.dto.store.CreateStoreRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.store.DeleteStoreRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.store.GetStoreResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.store.PagingStoreDto;
import com.capstone.hyperledgerfabrictransferserver.dto.storeimage.StoreImageModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.service.StoreService;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;

    @GetMapping("/user/store")
    public ResponseEntity<GetStoreResponse> getStore(
            @RequestParam @NonNull String name,
            @RequestParam @NonNull String phoneNumber
    )
    {
        return ResponseEntity.ok(storeService.getStore(name, phoneNumber));
    }

    @GetMapping("/user/stores")
    public ResponseEntity<PagingStoreDto> getAllStore(@RequestParam(defaultValue = "1") @ApiParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(storeService.getAllStore(page));
    }

    @PostMapping( "/admin/store")
    public ResponseEntity<Void> create(
            @RequestPart MultipartFile multipartFile,
            @RequestPart CreateStoreRequest createStoreRequest
    )
    {
        storeService.createStore(createStoreRequest, multipartFile);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/admin/store")
    public ResponseEntity<Void> delete(@RequestBody DeleteStoreRequest deleteStoreRequest) {
        storeService.deleteStore(deleteStoreRequest);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/admin/storeimage")
    public ResponseEntity<Void> modifyStoreImage(
            @RequestPart @Nullable MultipartFile multipartFile,
            @RequestPart StoreImageModifyRequest storeImageModifyRequest
    )
    {
        storeService.modifyStoreImageBy(storeImageModifyRequest, multipartFile);
        return ResponseEntity.ok(null);
    }
}
