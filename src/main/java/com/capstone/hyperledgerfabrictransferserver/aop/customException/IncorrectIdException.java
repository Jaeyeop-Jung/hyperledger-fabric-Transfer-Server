package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectIdException extends RuntimeException{
    public IncorrectIdException(String message) {
        super(message);
    }
}
