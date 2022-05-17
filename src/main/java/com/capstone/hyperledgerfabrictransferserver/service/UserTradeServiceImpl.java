package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectStudentIdException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTradeTransactionResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserTradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserTradeServiceImpl implements UserTradeService {

    private final UserService userService;
    private final FabricService fabricService;
    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private final UserTradeRepository userTradeRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public TransferResponse transfer(HttpServletRequest httpServletRequest, UserTransferRequest userTransferRequest) {

        Coin coin = coinRepository.findByName(userTransferRequest.getCoinName())
                .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인입니다"));
        User sender = userService.getUserByJwtToken(httpServletRequest);
        User receiver = userRepository.findByStudentId(userTransferRequest.getStudentId())
                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다"));

        userTradeRepository.save(
                UserTrade.of(
                        sender,
                        receiver,
                        coin,
                        userTransferRequest.getAmount()
                )
        );

        try {
            Gateway gateway = fabricService.getGateway();
            String transferResponse = fabricService.submitTransaction(
                    gateway,
                    "TransferCoin",
                    "asset" + sender.getId(),
                    "asset" + receiver.getId(),
                    coin.getName(),
                    String.valueOf(userTransferRequest.getAmount()));
            fabricService.close(gateway);

            return objectMapper.readValue(transferResponse, TransferResponse.class);
        } catch (Exception e) {
            throw new IncorrectContractException("TransferCoin 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Override
    @Transactional
    public List<UserTradeTransactionResponse> enquireUserTrade(HttpServletRequest httpServletRequest) {

        List<UserTrade> findUser = userTradeRepository.findAll(Sort.by(Sort.Direction.ASC, "dateCreated"));

        List<UserTradeTransactionResponse> responses = new ArrayList<>();
        for (UserTrade userTrade : findUser) {
            responses.add(
                    UserTradeTransactionResponse.builder()
                            .sender(userTrade.getSender().getStudentId())
                            .receiver(userTrade.getReceiver().getStudentId())
                            .coinName(userTrade.getCoin().getName())
                            .dateCreated(userTrade.getDateCreated())
                            .build()
            );
        }
        return responses;
    }

}
