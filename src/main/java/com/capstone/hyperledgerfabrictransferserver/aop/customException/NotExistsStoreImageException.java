package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class NotExistsStoreImageException extends RuntimeException {
    public NotExistsStoreImageException(String message) {
        super(message);
    }
}
