package com.capstone.hyperledgerfabrictransferserver.aop;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.AlreadyExistUserException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.EmptyTokenException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class JwtTokenExceptionHandler {

    @ExceptionHandler(EmptyTokenException.class)
    public ResponseEntity<Map<String, String>> EmptyTokenExceptionHandler(Exception e){

        HttpHeaders responseHeader = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.info("Advice : EmptyTokenExceptionHandler");

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeader, httpStatus);
    }

    @ExceptionHandler(IncorrectTokenException.class)
    public ResponseEntity<Map<String, String>> IncorrectTokenExceptionHandler(Exception e){

        HttpHeaders responseHeader = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.info("Advice : IncorrectTokenExceptionHandler");

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeader, httpStatus);
    }
}
