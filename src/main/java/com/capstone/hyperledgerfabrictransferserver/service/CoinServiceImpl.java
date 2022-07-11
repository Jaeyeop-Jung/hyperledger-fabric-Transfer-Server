package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.AlreadyExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
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
    public void create(CoinCreateRequest coinCreateRequest) {

        if(coinRepository.existsByName(coinCreateRequest.getCoinName())){
            throw new AlreadyExistsCoinException("이미 존재하는 코인입니다");
        }

        coinRepository.save(
                Coin.of(
                        coinCreateRequest.getCoinName(),
                        coinCreateRequest.getIssuance()
                )
        );

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "CreateCoin", coinCreateRequest.getCoinName());
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("CreateAsset 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Override
    @Transactional
    public void delete(CoinModifyRequest coinModifyRequest) {

        Coin findCoin = coinRepository.findByName(coinModifyRequest.getCoinName())
                .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인입니다"));
        findCoin.setDeleted();

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "DeleteCoin", coinModifyRequest.getCoinName());
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("DeleteCoin 체인코드 실행 중 오류가 발생했습니다");
        }
    }

}
