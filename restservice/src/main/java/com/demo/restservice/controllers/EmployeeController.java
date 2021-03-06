package com.demo.restservice.controllers;

import com.demo.restservice.dto.GetEmployeeDto;
import com.demo.restservice.dto.SavedEmployeeDto;
import com.demo.restservice.services.EmployeeService;
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
    private static final int AGE = 18; //21
    private static final String DIFF_FORMAT = "Years: %d - Months: %d - Days: %d";

    private EmployeeService employeeService;
    private ModelMapper modelMapper;

    @Autowired
    EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/employee")
    public @ResponseBody
    SavedEmployeeDto saveEmployee(
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String surname,
            @RequestParam @NotBlank String documentType,
            @RequestParam @NotBlank String documentNumber,
            @RequestParam @NotBlank String role,
            @RequestParam @NotBlank String salary,
            @RequestParam(name = "birthday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String birthDay,
            @RequestParam(name = "hireday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String hireDay
    ) throws Exception {

        if (getAge(birthDay) < AGE) {
            throw new Exception("error, the employee most be of legal age");
        }

        GetEmployeeDto getEmployeeDto = buildGetEmployeeDto(name, surname, documentType, documentNumber, role,
                salary, birthDay, hireDay);

        SaveEmployeeResponse saveEmployeeResponse = employeeService.saveEmployee(getEmployeeDto);

        if (!saveEmployeeResponse.getStatus().equals(OK)) {
            throw new Exception("an error occurred when trying to save the registry");
        }

        return buildSaveEmployeeDto(getEmployeeDto, birthDay, hireDay);
    }

    public GetEmployeeDto buildGetEmployeeDto(String name, String surname, String documentType, String documentNumber,
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

    private SavedEmployeeDto buildSaveEmployeeDto(GetEmployeeDto getEmployeeDto, String birthDay, String hireDay){
        SavedEmployeeDto savedEmployeeDto = modelMapper.map(getEmployeeDto, SavedEmployeeDto.class);
        savedEmployeeDto.setAge(getDiff(birthDay));
        savedEmployeeDto.setHireTime(getDiff(hireDay));
        return savedEmployeeDto;

    }

    public int getAge(String birthDay) {
        LocalDate localDateBirthDay = LocalDate.parse(birthDay, DateTimeFormatter.ISO_LOCAL_DATE);
        Period diff = Period.between(localDateBirthDay, LocalDate.now());
        return diff.getYears();
    }

    public String getDiff(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        Period diff = Period.between(localDate, LocalDate.now());
        return String.format(DIFF_FORMAT, diff.getYears(), diff.getMonths(), diff.getDays());
    }
}
