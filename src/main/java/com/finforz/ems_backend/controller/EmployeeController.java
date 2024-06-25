package com.finforz.ems_backend.controller;

import com.finforz.ems_backend.collection.Person;
import com.finforz.ems_backend.dto.EmployeeDto;
import com.finforz.ems_backend.exception.EmployeeServiceException;
import com.finforz.ems_backend.exception.GlobalErrorCode;
import com.finforz.ems_backend.service.EmployeeService;
import com.finforz.ems_backend.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Registration")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    // Build Add Employee Rest API
    @PostMapping("/addEmployee")
    @Operation(summary = "Employee Registration", description = "Employee Creation")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })
//    @PreAuthorize("hasAnyRole(@roleValidationService.getUserGroupRolesRightsByScreenId(\"8\"))") S8R,S8W
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email) throws EmployeeServiceException {
        EmployeeDto employeeDto = EmployeeDto.builder().firstName(firstName).lastName(lastName).email(email).build();
        EmployeeDto savedEmployee = null;
        try {
            savedEmployee = employeeService.createEmployee(employeeDto);
        } catch (Exception e) {
            throw new EmployeeServiceException(GlobalErrorCode._6003);
        }
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Build Get Employee Rest API
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get Employee by Id", description = "Get Employee by Id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId) {
        log.info("Fetching employee with id: {}", employeeId);
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    // Build Get All Employee Rest API
    @GetMapping("/allEmployees")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get All Employees", description = "Get All Employees")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDtoList);
    }

    // Build Update all fields in Employee Rest API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update Employee by Id", description = "Update Employee by Id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody EmployeeDto updateEmployeeDto) {
        log.info("Updating employee with id: {}", employeeId);
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updateEmployeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    // Build Update specific fields in Employee Rest API
    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Patch update Employee by Id", description = "Patch update Employee by Id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<EmployeeDto> patchEmployee(@PathVariable("id") Long employeeId, @RequestBody Map<String, Object> updateEmployeeFields) {
        log.info("Updating specific fields of employee with id: {}", employeeId);
        EmployeeDto employeeDto = employeeService.updateEmployeeBySpecificFields(employeeId, updateEmployeeFields);
        return ResponseEntity.ok(employeeDto);
    }

    // Build Delete Employee Rest API
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete Employee by Id", description = "Delete Employee by Id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) {
        log.info("Deleting employee with id: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully!.");
    }

}
