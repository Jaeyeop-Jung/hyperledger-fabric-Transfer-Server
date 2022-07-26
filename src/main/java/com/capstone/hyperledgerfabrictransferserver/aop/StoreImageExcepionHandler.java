package com.capstone.hyperledgerfabrictransferserver.aop;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.FailToWriteImageFileException;
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
public class StoreImageExcepionHandler {

    @ExceptionHandler(FailToWriteImageFileException.class)
    public ResponseEntity<Map<String, String>> FailToWriteImageFileExceptionHandler(Exception e){

        HttpHeaders responseHeader = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.info("Advice : FailToWriteImageFileExceptionHandler");

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeader, httpStatus);
    }
}
