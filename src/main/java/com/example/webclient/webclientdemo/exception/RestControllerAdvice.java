package com.example.webclient.webclientdemo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        System.out.println("aca");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}