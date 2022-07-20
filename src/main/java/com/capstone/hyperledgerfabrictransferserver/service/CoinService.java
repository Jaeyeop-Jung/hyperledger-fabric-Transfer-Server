package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.AlreadyExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectContractException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectStudentIdException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsCoinException;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinCreateRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.CoinModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UpdateAssetCoinRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinService {

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;
    private final FabricService fabricService;

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
            fabricService.submitTransaction(gateway, "CreateCoin", coinCreateRequest.getCoinName(), String.valueOf(coinCreateRequest.getIssuance()));
            fabricService.close(gateway);
        } catch (Exception e){
            throw new IncorrectContractException("CreateAsset 체인코드 실행 중 오류가 발생했습니다");
        }
    }

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

    @Transactional
    public void updateAllAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest) {
        if (coinRepository.existsByName(updateAssetCoinRequest.getCoinName())) {
            throw new NotExistsCoinException("존재하지 않은 코인입니다.");
        }

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "UpdateAllAssetCoin", updateAssetCoinRequest.getCoinName(), updateAssetCoinRequest.getCoinValue());
            fabricService.close(gateway);
        } catch (Exception e) {
            throw new IncorrectContractException("UpdateAllAssetCoin 체인코드 실행 중 오류가 발생했습니다");
        }
    }

    @Transactional
    public void updateAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest) {
        if (coinRepository.existsByName(updateAssetCoinRequest.getCoinName())) {
            throw new NotExistsCoinException("존재하지 않은 코인입니다.");
        }
        User findUser = userRepository.findByStudentId(updateAssetCoinRequest.getStudentId())
                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다"));

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "UpdateAssetCoin", "asset" + findUser.getStudentId(), updateAssetCoinRequest.getCoinName(), updateAssetCoinRequest.getCoinValue());
            fabricService.close(gateway);
        } catch (Exception e) {
            throw new IncorrectContractException("UpdateAssetCoin 체인코드 실행 중 오류가 발생했습니다");
        }
    }
}
