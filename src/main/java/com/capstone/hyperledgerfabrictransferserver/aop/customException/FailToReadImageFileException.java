package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class FailToReadImageFileException extends RuntimeException{
    public FailToReadImageFileException(String message) {
        super(message);
    }
}
