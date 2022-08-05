package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class NotExistsAdminException extends RuntimeException{

    public NotExistsAdminException(String message) {
        super(message);
    }
}
