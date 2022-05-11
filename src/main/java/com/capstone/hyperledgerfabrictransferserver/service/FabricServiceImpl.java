package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.util.CustomFabricGateway;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FabricServiceImpl implements FabricService{

    @Override
    @Transactional
    public void submitTransaction(String name, String... args) {
        Gateway connect = CustomFabricGateway.connect();

    }

    @Override
    @Transactional
    public void evaluateTransaction(String name, String... args) {

    }
}
