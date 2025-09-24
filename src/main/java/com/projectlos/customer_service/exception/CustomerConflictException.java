package com.projectlos.customer_service.exception;

import org.springframework.http.HttpStatus;

public class CustomerConflictException extends BaseException {
    
    public CustomerConflictException(String message) {
        super(message, HttpStatus.CONFLICT, "CUSTOMER_CONFLICT");
    }
    
    public CustomerConflictException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT, "CUSTOMER_CONFLICT");
    }
}
