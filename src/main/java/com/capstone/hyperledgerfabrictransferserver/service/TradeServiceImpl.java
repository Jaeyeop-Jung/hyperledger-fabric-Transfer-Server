package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectStudentIdException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeServiceImpl implements TradeService{

    private final UserService userService;
    private final FabricService fabricService;
    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private final TradeRepository tradeRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public TransferResponse transfer(HttpServletRequest httpServletRequest, TransferRequest transferRequest) {

        Coin coin = coinRepository.findByName(transferRequest.getCoinName())
                .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인입니다"));
        User sender = userService.getUserByJwtToken(httpServletRequest);
        User receiver = userRepository.findByStudentId(transferRequest.getReceiverStudentIdOrPhoneNumber())
                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다"));

        tradeRepository.save(
                Trade.of(
                        sender,
                        receiver,
                        coin,
                        transferRequest.getAmount()
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
                    String.valueOf(transferRequest.getAmount()));
            fabricService.close(gateway);

            return objectMapper.readValue(transferResponse, TransferResponse.class);
        } catch (Exception e) {
            throw new IncorrectContractException("TransferCoin 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponse> enquireTrade(HttpServletRequest httpServletRequest, int page) {

        User findUser = userService.getUserByJwtToken(httpServletRequest);
        Page<Trade> findAllUserTrades = tradeRepository.findAllBySenderOrReceiver(findUser, findUser, PageRequest.of(page - 1, 20, Sort.Direction.DESC, "dateCreated"));

        return TransferResponse.toDtoList(findAllUserTrades.getContent());
    }
}
