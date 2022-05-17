package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTradeTransactionResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserTradeService {

    public AssetDto transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest);

    public List<UserTradeTransactionResponse> transaction(HttpServletRequest httpServletRequest);
}
