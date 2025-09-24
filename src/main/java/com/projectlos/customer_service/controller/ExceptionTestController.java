package com.projectlos.customer_service.controller;

import com.projectlos.customer_service.exception.CustomerNotFoundException;
import com.projectlos.customer_service.exception.InvalidCustomerException;
import com.projectlos.customer_service.exception.CustomerConflictException;
import com.projectlos.customer_service.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/exception-test")
@Slf4j
@Tag(name = "Exception Test", description = "Test endpoints for customer exception handling")
public class ExceptionTestController {

    @GetMapping("/customer-not-found")
    @Operation(summary = "Test 404 Customer Not Found Exception")
    public ResponseEntity<String> testCustomerNotFoundException() {
        throw new CustomerNotFoundException("Test customer not found");
    }

    @GetMapping("/invalid-customer")
    @Operation(summary = "Test 400 Invalid Customer Exception")
    public ResponseEntity<String> testInvalidCustomerException() {
        throw new InvalidCustomerException("Test invalid customer data");
    }

    @GetMapping("/customer-conflict")
    @Operation(summary = "Test 409 Customer Conflict Exception")
    public ResponseEntity<String> testCustomerConflictException() {
        throw new CustomerConflictException("Test customer already exists");
    }

    @GetMapping("/unauthorized")
    @Operation(summary = "Test 401 Unauthorized Exception")
    public ResponseEntity<String> testUnauthorizedException() {
        throw new UnauthorizedException("Test unauthorized access to customer data");
    }

    @GetMapping("/runtime")
    @Operation(summary = "Test 500 Runtime Exception")
    public ResponseEntity<String> testRuntimeException() {
        throw new RuntimeException("Test runtime exception in customer service");
    }

    @GetMapping("/generic")
    @Operation(summary = "Test 500 Generic Exception")
    public ResponseEntity<String> testGenericException() throws Exception {
        throw new Exception("Test generic exception in customer service");
    }
}
