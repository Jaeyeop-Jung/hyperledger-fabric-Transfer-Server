package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TradeService {

    public TransferResponse transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest);

    public List<TransferResponse> enquireTrade(HttpServletRequest httpServletRequest, int page);
}
