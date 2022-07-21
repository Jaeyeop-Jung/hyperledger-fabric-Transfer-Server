package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectUniqueNumberException extends RuntimeException{
    public IncorrectUniqueNumberException(String message) {
        super(message);
    }
}
