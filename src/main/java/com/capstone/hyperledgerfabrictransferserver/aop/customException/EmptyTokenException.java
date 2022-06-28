package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class EmptyTokenException extends RuntimeException{
    public EmptyTokenException(String message) {
        super(message);
    }
}
