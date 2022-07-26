package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class NotFoundStoreException extends RuntimeException{
    public NotFoundStoreException(String message) {
        super(message);
    }
}
