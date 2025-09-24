package com.projectlos.customer_service.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseException {
    
    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND");
    }
    
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND");
    }
}
