package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UpdateAssetCoinRequest;
import com.capstone.hyperledgerfabrictransferserver.service.CoinService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CoinApiControllerImpl implements CoinApiController{

    private final CoinService coinService;

    @Override
    @PostMapping("/coin")
    public ResponseEntity<Void> create(@RequestBody CoinCreateRequest coinCreateRequest) {

        coinService.create(coinCreateRequest);

        return ResponseEntity.ok(null);
    }

    @Override
    @DeleteMapping("/coin")
    public ResponseEntity<Void> delete(@RequestBody CoinModifyRequest coinModifyRequest) {
        coinService.delete(coinModifyRequest);

        return ResponseEntity.ok(null);
    }

    @Override
    @PostMapping("/coin/update/all")
    public ResponseEntity<Void> UpdateAllAssetCoin(
            @RequestBody UpdateAssetCoinRequest updateAssetCoinRequest
    )
    {
        coinService.updateAllAssetCoin(updateAssetCoinRequest);
        return ResponseEntity.ok(null);
    }

    @Override
    @PostMapping("/coin/update")
    public ResponseEntity<Void> updateAssetCoin(
            @RequestBody UpdateAssetCoinRequest updateAssetCoinRequest
    )
    {
        coinService.updateAssetCoin(updateAssetCoinRequest);
        return ResponseEntity.ok(null);
    }
}
