package com.example.webclient.webclientdemo.exception;

public class CustomException extends Exception{

    private static final long serialVersionUID = 1L;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public CustomException(String message) {
        super();
        this.message = message;
    }
}