package com.demo.soapservice.endpoints;

import com.demo.soapservice.services.EmployeeService;
import com.demo.soapservice.models.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.demo.SaveEmployeeResponse;
import io.demo.SaveEmployeeRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Endpoint
public class EmployeeController {
    private static final String NAMESPACE_URI = "http://demo.io/";
    private static final String LOCAL_PART = "saveEmployeeRequest";
    private static final String DATE_FORMAT = "yyyy-mm-dd";
    private static final String OK = "ok";

    private EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = LOCAL_PART)
    public SaveEmployeeResponse saveEmployee(@RequestPayload SaveEmployeeRequest request)
            throws java.text.ParseException {
        employeeService.save(buildEntity(request.getEmployee()));
        SaveEmployeeResponse response = new SaveEmployeeResponse();
        response.setStatus(OK);
        return response;
    }

    private Employee buildEntity(io.demo.Employee employee) throws java.text.ParseException {

        com.demo.soapservice.models.Employee employeeEntity = new com.demo.soapservice.models.Employee();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        employeeEntity.setName(employee.getName());
        employeeEntity.setSurname(employee.getSurname());
        employeeEntity.setDocumentType(employee.getDocumentType());
        employeeEntity.setDocumentNumber(employee.getDocumentNumber());
        employeeEntity.setBirthDay(dateFormat.parse(employee.getBirthDay()));
        employeeEntity.setHireDay(dateFormat.parse(employee.getHireDay()));
        employeeEntity.setRole(employee.getRole());
        employeeEntity.setSalary(employee.getSalary());
        return employeeEntity;
    }
}
