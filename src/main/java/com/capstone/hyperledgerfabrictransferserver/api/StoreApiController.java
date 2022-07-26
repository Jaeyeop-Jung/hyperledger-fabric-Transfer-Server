package com.capstone.hyperledgerfabrictransferserver.api;


import com.capstone.hyperledgerfabrictransferserver.dto.StoreCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;

    @PostMapping(value = "/admin/store")
    public ResponseEntity<Void> create(
            @RequestPart MultipartFile multipartFile,
            @RequestPart StoreCreateRequest storeCreateRequest
    )
    {
        storeService.createStore(storeCreateRequest, multipartFile);
        return ResponseEntity.ok(null);
    }
}
