package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.*;
import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.dto.coin.*;
import com.capstone.hyperledgerfabrictransferserver.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinService {

    private final CoinRepository coinRepository;
    private final UserService userService;
    private final FabricService fabricService;

    @Transactional(readOnly = true)
    public Coin getByCoinName(String coinName) {
        return coinRepository.findByName(coinName)
                .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인입니다"));
    }

    @Transactional(readOnly = true)
    public PagingCoinDto getAllCoin(int page) {
        Page<Coin> findAllCoin = coinRepository.findAll(PageRequest.of(page - 1, 20, Sort.Direction.ASC, "name"));
        return PagingCoinDto.from(findAllCoin);
    }

    @Transactional
    public void create(CoinCreateRequest coinCreateRequest) {

        if(coinRepository.existsByName(coinCreateRequest.getCoinName())){
            throw new AlreadyExistsCoinException("이미 존재하는 코인입니다");
        }

        coinRepository.save(
                Coin.of(
                        coinCreateRequest.getCoinName(),
                        0L
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

    @Transactional
    public void delete(CoinModifyRequest coinModifyRequest) {
        Gateway gateway = fabricService.getGateway();
        for (String coinName : coinModifyRequest.getCoinNameList()) {
            Coin findCoin = coinRepository.findByName(coinName)
                    .orElseThrow(() -> new NotExistsCoinException("존재하지 않은 코인입니다"));
            findCoin.setDeleted();

            try {
                fabricService.submitTransaction(gateway, "DeleteCoin", coinName);
            } catch (Exception e){
                throw new IncorrectContractException("DeleteCoin 체인코드 실행 중 오류가 발생했습니다");
            }
        }
        fabricService.close(gateway);
    }

    @Transactional
    public void updateAllAssetCoin(UpdateAllAssetCoinRequest updateAllAssetCoinRequest) {
        if (!coinRepository.existsByName(updateAllAssetCoinRequest.getCoinName())) {
            throw new NotExistsCoinException("존재하지 않은 코인입니다");
        }

        try {
            Gateway gateway = fabricService.getGateway();
            fabricService.submitTransaction(gateway, "UpdateAllAssetCoin", updateAllAssetCoinRequest.getCoinName(), updateAllAssetCoinRequest.getCoinValue(), updateAllAssetCoinRequest.getUserRole().name());
            fabricService.close(gateway);
        } catch (Exception e) {
            throw new IncorrectContractException("UpdateAllAssetCoin 체인코드 실행 중 오류가 발생했습니다");
        }

        Coin coin = coinRepository.findByName(updateAllAssetCoinRequest.getCoinName()).get();
        coin.modifyIssuance(
                coin.getIssuance() + userService.getNumberOfUserByUserRole(updateAllAssetCoinRequest.getUserRole()) * Long.valueOf(updateAllAssetCoinRequest.getCoinValue())
        );
    }

    @Transactional
    public void updateAssetCoin(UpdateAssetCoinRequest updateAssetCoinRequest) {
        if (!coinRepository.existsByName(updateAssetCoinRequest.getCoinName())) {
            throw new NotExistsCoinException("존재하지 않은 코인입니다.");
        }
        Gateway gateway = fabricService.getGateway();

        for (String identifier : updateAssetCoinRequest.getIdentifier()) {
            User findUser = userService.getUserByIdentifier(identifier);

            try {
                fabricService.submitTransaction(gateway, "UpdateAssetCoin", "asset" + findUser.getIdentifier(), updateAssetCoinRequest.getCoinName(), updateAssetCoinRequest.getCoinValue());
            } catch (Exception e) {
                throw new IncorrectContractException("UpdateAssetCoin 체인코드 실행 중 오류가 발생했습니다");
            }
        }

        Coin coin = coinRepository.findByName(updateAssetCoinRequest.getCoinName()).get();
        coin.modifyIssuance(coin.getIssuance() + Long.valueOf(updateAssetCoinRequest.getCoinValue()) * updateAssetCoinRequest.getIdentifier().size());

        fabricService.close(gateway);
    }

}
