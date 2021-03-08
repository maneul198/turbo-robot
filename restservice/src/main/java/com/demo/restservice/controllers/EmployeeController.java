package com.demo.restservice.controllers;

import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.restservice.dto.SavedEmployeeDto;
import com.demo.restservice.exceptions.ILegalAgeException;
import com.demo.restservice.exceptions.SoapConsumeException;
import com.demo.restservice.services.IEmployeeService;
import com.demo.soap.wsdl.SaveEmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Validated
@RestController
public class EmployeeController {

    private static final String OK = "ok";
    private static final String AGE_ERROR  = "error, the employee most be of legal age";
    private static final String CONSUME_SOAP_ERROR  = "an error occurred when trying to consume SOAP service";
    private static final int AGE = 18; //21

    private IEmployeeService employeeService;
    private ModelMapper modelMapper;

    @Autowired
    EmployeeController(IEmployeeService iEmployeeService, ModelMapper modelMapper) {
        this.employeeService = iEmployeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/employee")
    public @ResponseBody
    SavedEmployeeDto saveEmployee(
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String surname,
            @RequestParam(name = "documenttype") @NotBlank String documentType,
            @RequestParam(name = "documentnumber") @NotBlank String documentNumber,
            @RequestParam @NotBlank String role,
            @RequestParam @NotBlank String salary,
            @RequestParam(name = "birthday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String birthDay,
            @RequestParam(name = "hireday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String hireDay
    ) throws SoapConsumeException, ILegalAgeException {

        if (getAge(birthDay) < AGE) {
            throw new ILegalAgeException(AGE_ERROR);
        }

        GetEmployeeDto getEmployeeDto = buildGetEmployeeDto(name, surname, documentType, documentNumber, role,
                salary, birthDay, hireDay);

        SaveEmployeeResponse saveEmployeeResponse = employeeService.saveEmployee(getEmployeeDto);

        if (!saveEmployeeResponse.getStatus().equals(OK)) {
            throw new SoapConsumeException(CONSUME_SOAP_ERROR);
        }

        return buildSaveEmployeeDto(getEmployeeDto);
    }

    private GetEmployeeDto buildGetEmployeeDto(String name, String surname, String documentType, String documentNumber,
                                        String role, String salary, String birthDay, String hireDay) {

        GetEmployeeDto getEmployeeDto = new GetEmployeeDto();
        getEmployeeDto.setName(name);
        getEmployeeDto.setSurname(surname);
        getEmployeeDto.setDocumentType(documentType);
        getEmployeeDto.setDocumentNumber(documentNumber);
        getEmployeeDto.setBirthDay(birthDay);
        getEmployeeDto.setHireDay(hireDay);
        getEmployeeDto.setRole(role);
        getEmployeeDto.setSalary(salary);
        return getEmployeeDto;
    }

    private SavedEmployeeDto buildSaveEmployeeDto(GetEmployeeDto getEmployeeDto){
        SavedEmployeeDto savedEmployeeDto = modelMapper.map(getEmployeeDto, SavedEmployeeDto.class);
        savedEmployeeDto.setHireTime();
        savedEmployeeDto.setAge();
        return savedEmployeeDto;
    }

    private int getAge(String birthDay) {
        LocalDate localDateBirthDay = LocalDate.parse(birthDay, DateTimeFormatter.ISO_LOCAL_DATE);
        Period diff = Period.between(localDateBirthDay, LocalDate.now());
        return diff.getYears();
    }
}
