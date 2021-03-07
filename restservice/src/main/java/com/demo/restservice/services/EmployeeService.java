package com.demo.restservice.services;

import com.demo.restservice.clients.IEmployeeClient;
import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.soap.wsdl.SaveEmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService{

    private IEmployeeClient employeeClient;

    @Autowired
    EmployeeService(IEmployeeClient iEmployeeClient){
        this.employeeClient = iEmployeeClient;
    }

    public SaveEmployeeResponse saveEmployee(GetEmployeeDto employee) {
       return employeeClient.saveEmployee(employee);
    }
}
