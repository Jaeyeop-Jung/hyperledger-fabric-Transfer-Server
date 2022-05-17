package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.util.CustomFabricGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FabricServiceImpl implements FabricService{

    private final CustomFabricGateway customFabricGateway;
    private final ObjectMapper objectMapper;

    @Override
    public Gateway getGateway(){
        return customFabricGateway.connect();
    }

    /**
     * methodName : submitTransaction
     * author : Jaeyeop Jung
     * description : 패브릭 네트워크에 응답에 따라 boolean형과 Json형 두가지를 응답하기 위해서 Object사용
     *
     * @param connect the connect
     * @param name    the name
     * @param args    the args
     * @return the transaction result
     * @throws ContractException    the contract exception
     * @throws InterruptedException the interrupted exception
     * @throws TimeoutException     the timeout exception
     */
    @Override
    public Object submitTransaction(Gateway connect, String name, String ... args) throws ContractException, InterruptedException, TimeoutException, JsonProcessingException {
        Network network = connect.getNetwork("mychannel");
        Contract contract = network.getContract("basic");

        byte[] fabricResponse = contract.submitTransaction(name, args);

        String response = new String(fabricResponse, StandardCharsets.UTF_8);
        if(response.equals("true") || response.equals("false")){

            return Boolean.valueOf(response);

        } else {

            return objectMapper.readValue(response, AssetDto.class);
        }
    }

    @Override
    @Async
    public void close(Gateway connect){
        connect.close();
    }

}
