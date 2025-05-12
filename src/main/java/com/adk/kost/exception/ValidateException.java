package com.adk.kost.exception;

public class ValidateException extends RuntimeException{
    private String message;

    public ValidateException(String msg, Throwable cause){
        super(msg, cause);
        this.message = msg;
    }

    public ValidateException(String msg){
        super(msg);
        this.message = msg;
    }
}
