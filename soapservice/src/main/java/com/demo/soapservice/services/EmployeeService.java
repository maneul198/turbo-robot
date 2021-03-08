package com.demo.soapservice.services;

import com.demo.soapservice.exceptions.DuplicateEmployeeException;
import com.demo.soapservice.models.Employee;
import com.demo.soapservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService{

    private EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void save(Employee employee) throws DuplicateEmployeeException{
        if (employeeRepository.existsById(employee.getDocumentNumber())){
            throw new DuplicateEmployeeException(String.format(
                    "error, the employee with document number: %s already exist", employee.getDocumentNumber()));
        }
        employeeRepository.save(employee);
    }
}
