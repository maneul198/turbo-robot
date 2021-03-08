package com.demo.restservice.exceptions;

public class SoapConsumeException extends Exception{
    public SoapConsumeException(String errorMessage){
        super(errorMessage);
    }
}
