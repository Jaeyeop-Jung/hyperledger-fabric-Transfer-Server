package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class AlreadyExistsStoreException extends RuntimeException{
    public AlreadyExistsStoreException(String message) {
        super(message);
    }
}
