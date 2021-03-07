package com.demo.restservice.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class SavedEmployeeDto {

    private static final String DIFF_FORMAT = "Years: %d - Months: %d - Days: %d";

    private String name;
    private String surname;
    private String documentType;
    private String documentNumber;
    private String birthDay;
    private String hireDay;
    private String role;
    private String salary;
    private String hireTime;
    private String age;

    public String getHireTime() {
        return hireTime;
    }

    public void setHireTime() {
        this.hireTime = getDiff(hireDay);
    }

    public String getAge() {
        return age;
    }

    public void setAge() {
        this.age = getDiff(birthDay);
    }

    private String getDiff(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        Period diff = Period.between(localDate, LocalDate.now());
        return String.format(DIFF_FORMAT, diff.getYears(), diff.getMonths(), diff.getDays());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getHireDay() {
        return hireDay;
    }

    public void setHireDay(String hireDay) {
        this.hireDay = hireDay;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
