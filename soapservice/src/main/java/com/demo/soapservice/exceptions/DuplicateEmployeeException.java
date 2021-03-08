package com.demo.soapservice.exceptions;

public class DuplicateEmployeeException extends Exception{
    public DuplicateEmployeeException(String errMessage){
        super(errMessage);
    }
}
