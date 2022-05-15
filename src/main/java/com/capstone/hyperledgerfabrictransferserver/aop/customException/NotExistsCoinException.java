package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class NotExistsCoinException extends RuntimeException{
    public NotExistsCoinException(String message) {
        super(message);
    }
}
