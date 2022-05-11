package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserTradeService {

    public void transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest);

}
