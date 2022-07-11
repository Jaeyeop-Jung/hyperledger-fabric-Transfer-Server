package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UpdateAssetCoinRequest;

public interface CoinService {

    public void create(CoinCreateRequest coinCreateRequest);

    public void delete(CoinModifyRequest coinModifyRequest);

    public void updateAllAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest);

    public void updateAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest);
}
