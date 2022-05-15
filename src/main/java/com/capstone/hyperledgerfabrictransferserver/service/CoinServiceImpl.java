package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.AlreadyExistUserException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinServiceImpl implements CoinService{

    private final CoinRepository coinRepository;
    private final FabricService fabricService;

    @Override
    @Transactional
    public void create(String coinName){

        if(coinRepository.existsByName(coinName)){
            throw new AlreadyExistUserException("이미 존재하는 코인입니다");
        }

        coinRepository.save(Coin.of(coinName));

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "CreateCoin", coinName);
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("CreateCoin 체인코드 실행 중 오류가 발생했습니다");
        }
    }

}
