package com.capstone.hyperledgerfabrictransferserver.api.user;

import com.capstone.hyperledgerfabrictransferserver.service.StoreImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class StoreImageController {

    private final StoreImageService storeImageService;

    @GetMapping(
            value = "/storeimage",
            produces = {
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE
            }
    )
    public ResponseEntity<byte[]> getStoreImage(@NonNull String fileName) {
        return ResponseEntity.ok(storeImageService.getStoreImage(fileName));
    }

}
