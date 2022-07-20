package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectTransactionIdException extends RuntimeException{
    public IncorrectTransactionIdException(String message) {
        super(message);
    }
}
