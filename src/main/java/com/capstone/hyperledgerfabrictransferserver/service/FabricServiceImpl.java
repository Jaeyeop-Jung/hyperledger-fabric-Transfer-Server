package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.util.CustomFabricGateway;
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
public class FabricServiceImpl {

    private final CustomFabricGateway customFabricGateway;

    public Gateway getGateway(){
        return customFabricGateway.connect();
    }

    public JsonObject submitTransaction(Gateway connect, String name, String ... args) throws ContractException, InterruptedException, TimeoutException {
        Network network = connect.getNetwork("mychannel");
        Contract contract = network.getContract("basic");

        byte[] fabricResponse = contract.submitTransaction(name, args);

        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(new String(fabricResponse));

        return parse.getAsJsonObject();
    }

    @Async
    public void close(Gateway connect){
        connect.close();
    }

}
