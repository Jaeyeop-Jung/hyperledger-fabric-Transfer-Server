package com.capstone.hyperledgerfabrictransferserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;

import java.util.concurrent.TimeoutException;

public interface FabricService {

    public Gateway getGateway();

    public Object submitTransaction(Gateway connect, String name, String ... args) throws ContractException, InterruptedException, TimeoutException, JsonProcessingException;

    public void close(Gateway connect);
}
