package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectContractException extends RuntimeException{
    public IncorrectContractException(String message) {
        super(message);
    }
}
