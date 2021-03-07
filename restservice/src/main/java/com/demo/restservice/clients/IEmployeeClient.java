package com.demo.restservice.clients;

import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.soap.wsdl.SaveEmployeeResponse;

public interface IEmployeeClient {
    SaveEmployeeResponse saveEmployee(GetEmployeeDto employee);
}
