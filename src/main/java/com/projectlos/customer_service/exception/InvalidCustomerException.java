package com.projectlos.customer_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidCustomerException extends BaseException {
    
    public InvalidCustomerException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_CUSTOMER");
    }
    
    public InvalidCustomerException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST, "INVALID_CUSTOMER");
    }
}
