package com.demo.restservice.clients;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.soap.wsdl.SaveEmployeeRequest;
import com.demo.soap.wsdl.SaveEmployeeResponse;
import com.demo.soap.wsdl.Employee;

public class EmployeeClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(EmployeeClient.class);
    private static final String URI = "http://localhost:8081/ws/employees";
    private static final String SOAP_ACTION = "http://demo.io/saveEmployeeRequest";

    @Autowired
    ModelMapper modelMapper;

    private Employee buildEmployee(GetEmployeeDto getEmployee) {
        Employee employee = modelMapper.map(getEmployee, Employee.class);
        return employee;
}

    public SaveEmployeeResponse saveEmployee(GetEmployeeDto employee) {
        SaveEmployeeRequest request = new SaveEmployeeRequest();
        request.setEmployee(buildEmployee(employee));
        log.info("Requesting location for " + employee);

        SaveEmployeeResponse response = (SaveEmployeeResponse) getWebServiceTemplate()
                .marshalSendAndReceive(URI, request, new SoapActionCallback(SOAP_ACTION));
        return response;
    }
}
