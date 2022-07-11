package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UpdateAssetCoinRequest;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface CoinApiController {

    public ResponseEntity<Void> create(CoinCreateRequest coinCreateRequest);

    public ResponseEntity<Void> delete(CoinModifyRequest coinModifyRequest);

    public ResponseEntity<Void> UpdateAllAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest);

    public ResponseEntity<Void> updateAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest);
}
