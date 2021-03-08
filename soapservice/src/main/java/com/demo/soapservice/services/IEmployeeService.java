package com.demo.soapservice.services;

import com.demo.soapservice.exceptions.DuplicateEmployeeException;
import com.demo.soapservice.models.Employee;

public interface IEmployeeService {
    void save(Employee employee) throws DuplicateEmployeeException;
}
