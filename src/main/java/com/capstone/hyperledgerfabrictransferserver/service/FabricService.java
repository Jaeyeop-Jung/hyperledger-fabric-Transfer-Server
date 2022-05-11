package com.capstone.hyperledgerfabrictransferserver.service;

public interface FabricService {

    public void submitTransaction(String name, String... args);

    public void evaluateTransaction(String name, String... args);
}
