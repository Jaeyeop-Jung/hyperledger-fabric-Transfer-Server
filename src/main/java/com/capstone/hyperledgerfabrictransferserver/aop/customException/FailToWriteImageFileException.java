package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class FailToWriteImageFileException extends RuntimeException{
    public FailToWriteImageFileException(String message) {
        super(message);
    }
}
