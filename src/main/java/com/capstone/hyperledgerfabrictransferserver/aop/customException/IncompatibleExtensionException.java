package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncompatibleExtensionException extends RuntimeException{
    public IncompatibleExtensionException(String message) {
        super(message);
    }
}
