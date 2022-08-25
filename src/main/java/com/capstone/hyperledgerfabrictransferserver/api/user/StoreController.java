package com.capstone.hyperledgerfabrictransferserver.api.user;


import com.capstone.hyperledgerfabrictransferserver.dto.store.GetStoreResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.store.PagingStoreDto;
import com.capstone.hyperledgerfabrictransferserver.service.StoreService;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store")
    public ResponseEntity<GetStoreResponse> getStore(
            @RequestParam @NonNull String name,
            @RequestParam @NonNull String phoneNumber
    )
    {
        return ResponseEntity.ok(storeService.getStore(name, phoneNumber));
    }

    @GetMapping("/stores")
    public ResponseEntity<PagingStoreDto> getAllStore(@RequestParam(defaultValue = "1") @ApiParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(storeService.getAllStore(page));
    }

}
