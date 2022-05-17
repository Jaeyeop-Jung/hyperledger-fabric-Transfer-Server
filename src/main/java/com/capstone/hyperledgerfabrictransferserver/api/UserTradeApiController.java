package com.capstone.hyperledgerfabrictransferserver.api;

import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTradeTransactionResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.google.api.Http;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserTradeApiController {

    ResponseEntity<TransferResponse> transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest);

    ResponseEntity<List<UserTradeTransactionResponse>> enquireUserTrade(HttpServletRequest httpServletRequest);

}
