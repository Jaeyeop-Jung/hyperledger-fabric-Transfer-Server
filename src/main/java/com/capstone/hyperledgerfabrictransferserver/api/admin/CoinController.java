package com.capstone.hyperledgerfabrictransferserver.api.admin;

import com.capstone.hyperledgerfabrictransferserver.dto.coin.*;
import com.capstone.hyperledgerfabrictransferserver.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/coins")
    public ResponseEntity<getAllCoinDto> getAllCoin() {
        return ResponseEntity.ok(coinService.getAllCoin());
    }

    @PostMapping("/coin")
    public ResponseEntity<Void> create(@RequestBody CoinCreateRequest coinCreateRequest) {

        coinService.create(coinCreateRequest);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/coin")
    public ResponseEntity<Void> delete(@RequestBody CoinModifyRequest coinModifyRequest) {
        coinService.delete(coinModifyRequest);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/coin/update/assets")
    public ResponseEntity<Void> UpdateAllAssetCoin(
            @RequestBody UpdateAllAssetCoinRequest updateAllAssetCoinRequest
    )
    {
        coinService.updateAllAssetCoin(updateAllAssetCoinRequest);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/coin/update/asset")
    public ResponseEntity<Void> updateAssetCoin(
            @RequestBody UpdateAssetCoinRequest updateAssetCoinRequest
    )
    {
        coinService.updateAssetCoin(updateAssetCoinRequest);
        return ResponseEntity.ok(null);
    }

}
