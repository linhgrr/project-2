package com.demo.demo.customexception;

public class FieldRequiredException extends RuntimeException{
    public FieldRequiredException(String message) {
        super(message);
    }
}
