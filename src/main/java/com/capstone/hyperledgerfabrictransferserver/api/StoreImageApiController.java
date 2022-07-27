package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.service.StoreImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StoreImageApiController {

    private final StoreImageService storeImageService;

    @GetMapping(
            value = "/user/storeimage",
            produces = {
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE
            }
    )
    public ResponseEntity<byte[]> getStoreImage(@NonNull String fileName) {
        return ResponseEntity.ok(storeImageService.getStoreImage(fileName));
    }

}
