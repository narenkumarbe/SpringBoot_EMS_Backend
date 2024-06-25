package com.finforz.ems_backend.service;

import com.finforz.ems_backend.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long employeeId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployeeDto);

    void deleteEmployee(Long employeeId);

    EmployeeDto updateEmployeeBySpecificFields(Long employeeId, Map<String, Object> updateEmployeeFields);
}
