package com.demo.restservice.services;

import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.soap.wsdl.SaveEmployeeResponse;

public interface IEmployeeService {
    SaveEmployeeResponse saveEmployee(GetEmployeeDto employee);
}
