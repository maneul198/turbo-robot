package com.demo.restservice.services;

import com.demo.restservice.clients.EmployeeClient;
import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.soap.wsdl.SaveEmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeClient employeeClient;

    @Autowired
    EmployeeService(EmployeeClient employeeClient){
        this.employeeClient = employeeClient;
    }

    public SaveEmployeeResponse saveEmployee(GetEmployeeDto employee) {
       return employeeClient.saveEmployee(employee);
    }
}
