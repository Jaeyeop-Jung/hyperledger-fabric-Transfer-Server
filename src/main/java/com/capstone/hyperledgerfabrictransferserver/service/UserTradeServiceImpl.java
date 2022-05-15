package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectStudentIdException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserTradeRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class UserTradeServiceImpl implements UserTradeService{

    private final UserService userService;
    private final FabricService fabricService;
    private final UserRepository userRepository;
    private final UserTradeRepository userTradeRepository;
    private final CoinRepository coinRepository;

    private final CoinService coinService;

    @Override
    @Transactional
    public void transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest) {

        Coin requestCoin = coinRepository.findByName(transferRequest.getCoinName())
                .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인으로 요청했습니다"));
        User sender = userService.getUserByJwtToken(httpServletRequest);
        User receiver = userRepository.findByStudentId(transferRequest.getReceiverStudentId())
                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다"));

        userTradeRepository.save(
                UserTrade.of(
                        sender,
                        receiver,
                        requestCoin,
                        transferRequest.getAmount()
                )
        );

        try {
            Gateway gateway = fabricService.getGateway();
            Object response = fabricService.submitTransaction(
                    gateway,
                    "TransferCoin",
                    "asset" + sender.getId(),
                    "asset" + receiver.getId(),
                    requestCoin.getName(),
                    String.valueOf(transferRequest.getAmount())
            );
            JsonObject response1 = (JsonObject) response;
            System.out.println("response1 = " + response1);
            fabricService.close(gateway);
        } catch (Exception e) {
            throw new IncorrectContractException("TransferCoin 체인코드 실행 중 오류가 발생했습니다");
        }

    }

}
