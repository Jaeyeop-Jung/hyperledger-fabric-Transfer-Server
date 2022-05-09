package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class DeletedUserException extends RuntimeException{
    public DeletedUserException(String message) {
        super(message);
    }
}
