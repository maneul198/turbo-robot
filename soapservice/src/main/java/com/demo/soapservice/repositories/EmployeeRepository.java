package com.demo.soapservice.repositories;

import com.demo.soapservice.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
