package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectTokenException extends RuntimeException{
    public IncorrectTokenException(String message) {
        super(message);
    }
}
