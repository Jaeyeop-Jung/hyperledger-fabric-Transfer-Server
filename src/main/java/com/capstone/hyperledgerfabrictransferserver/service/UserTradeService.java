package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserTradeService {

    public AssetDto transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest);

}
