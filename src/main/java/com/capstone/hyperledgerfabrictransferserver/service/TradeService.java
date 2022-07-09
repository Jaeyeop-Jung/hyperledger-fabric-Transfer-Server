package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.PagingTransferResponseDto;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.google.api.Http;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface TradeService {

    public TransferResponse transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest);

    public List<TransferResponse> enquireTrade(HttpServletRequest httpServletRequest, int page);

    public PagingTransferResponseDto getAllTradeBy(int page, Long sender, Long receiver, LocalDateTime dateCreated);
}
