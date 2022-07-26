package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.service.StoreImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StoreImageApiController {

//    private final StoreImageService storeImageService;
//
//    @PatchMapping("/admin/storeimage")
//    public ResponseEntity<Void> modifyStoreImage(@RequestPart MultipartFile multipartFile) {
//        storeImageService.modifyStoreImage(multipartFile);
//        return ResponseEntity.ok(null);
//    }

}
