package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class AlreadyExistUserException extends RuntimeException{
    public AlreadyExistUserException(String message) {
        super(message);
    }
}
