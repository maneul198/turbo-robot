package com.demo.restservice.exceptions;

public class NotLegalAgeException extends RuntimeException{
    public NotLegalAgeException(String errorMessage){
        super(errorMessage);
    }
}
