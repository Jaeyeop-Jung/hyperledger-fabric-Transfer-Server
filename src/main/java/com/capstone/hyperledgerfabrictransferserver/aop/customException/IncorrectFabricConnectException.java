package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectFabricConnectException extends RuntimeException{
    public IncorrectFabricConnectException(String message) {
        super(message);
    }
}
