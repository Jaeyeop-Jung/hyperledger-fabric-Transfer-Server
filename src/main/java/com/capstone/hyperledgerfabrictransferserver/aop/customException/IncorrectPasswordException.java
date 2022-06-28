package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
