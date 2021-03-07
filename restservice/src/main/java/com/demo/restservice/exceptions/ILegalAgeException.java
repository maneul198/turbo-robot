package com.demo.restservice.exceptions;

public class ILegalAgeException extends RuntimeException{
    public ILegalAgeException(String errorMessage){
        super(errorMessage);
    }
}
