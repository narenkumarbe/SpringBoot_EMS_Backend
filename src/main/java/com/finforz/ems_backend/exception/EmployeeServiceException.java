package com.finforz.ems_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeServiceException extends Exception{

    public EmployeeServiceException(String message) {
        super(message);
    }

}
