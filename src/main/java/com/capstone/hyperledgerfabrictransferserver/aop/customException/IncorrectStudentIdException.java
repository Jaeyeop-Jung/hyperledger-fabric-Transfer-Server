package com.capstone.hyperledgerfabrictransferserver.aop.customException;

public class IncorrectStudentIdException extends RuntimeException{
    public IncorrectStudentIdException(String message) {
        super(message);
    }
}
