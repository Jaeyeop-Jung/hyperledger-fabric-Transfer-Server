package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectIdentifierException extends RuntimeException{
    public IncorrectIdentifierException(String message) {
        super(message);
    }
}
