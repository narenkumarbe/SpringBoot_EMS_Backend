package com.finforz.ems_backend.advice;

import com.finforz.ems_backend.exception.ApiErrorResponse;
import com.finforz.ems_backend.exception.EmployeeServiceException;
import com.finforz.ems_backend.exception.GlobalErrorCode;
import com.finforz.ems_backend.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(exception.getMessage())
                .build();
        log.error("GlobalExceptionHandler::RuntimeException exception caught {}",exception.getMessage());

        return ResponseEntity.internalServerError().body(errorResponse);
    }
    @ExceptionHandler({EmployeeServiceException.class})
    public ResponseEntity<Object> handleRuntimeException(EmployeeServiceException exception) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorCode("6003")
                .errorMessage(exception.getMessage())
                .build();
        log.error("GlobalExceptionHandler::EmployeeServiceException exception caught {}",exception.getMessage());

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
