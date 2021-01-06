package com.setup.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlyMyCustomException(Exception e) {
        logger.error("error occurred {}", e);
        return new ResponseEntity<>("Error Occurred: " + e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }
}
