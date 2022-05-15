package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;

public interface CoinService {

    public void create(CoinCreateRequest coinCreateRequest);

    public void delete(CoinModifyRequest coinModifyRequest);
}
