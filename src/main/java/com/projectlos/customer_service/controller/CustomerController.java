package com.projectlos.customer_service.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectlos.customer_service.dto.request.CustomerRequest;
import com.projectlos.customer_service.dto.response.CustomerResponse;
import com.projectlos.customer_service.dto.response.CustomerSummaryResponse;
import com.projectlos.customer_service.enums.CustomerStatus;
import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;
import com.projectlos.customer_service.exception.CustomerNotFoundException;
import com.projectlos.customer_service.exception.InvalidCustomerException;
import com.projectlos.customer_service.exception.CustomerConflictException;
import com.projectlos.customer_service.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer Management", description = "APIs for customer management")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @PostMapping
    @Operation(summary = "Create new customer", description = "Register a new customer in the system")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        log.info("Creating new customer with email: {}", request.getEmail());
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieve customer information by ID")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(description = "Customer ID") @PathVariable("id") UUID id) {
        log.info("Fetching customer with ID: {}", id);
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieve all customers with pagination and optional search/filter parameters (summary view)")
    public ResponseEntity<Page<CustomerSummaryResponse>> getAllCustomers(
            @Parameter(description = "Search term for name, email, phone, ID number, city, or province") 
            @RequestParam(value="search", required = false) String search,
            @Parameter(description = "Filter by gender") 
            @RequestParam(value="gender", required = false) Gender gender,
            @Parameter(description = "Filter by ID type") 
            @RequestParam(value="idType", required = false) IdType idType,
            @Parameter(description = "Filter by marital status") 
            @RequestParam(value="maritalStatus",required = false) MaritalStatus maritalStatus,
            @Parameter(description = "Filter by customer status") 
            @RequestParam(value="status", required = false) CustomerStatus status,
            Pageable pageable) {
        log.info("Fetching customers with search: {}, gender: {}, idType: {}, maritalStatus: {}, status: {}", 
                search, gender, idType, maritalStatus, status);
        
        Page<CustomerSummaryResponse> response;
        
        // Check if any search or filter parameters are provided
        if (search != null || gender != null || idType != null || maritalStatus != null || status != null) {
            response = customerService.getAllCustomersSummaryWithSearch(search, gender, idType, maritalStatus, status, pageable);
        } else {
            response = customerService.getAllCustomersSummary(pageable);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Update customer information and related data")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(description = "Customer ID") @PathVariable("id") UUID id,
            @Valid @RequestBody CustomerRequest request) {
        log.info("Updating customer with ID: {}", id);
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update customer status", description = "Update customer verification status")
    public ResponseEntity<CustomerResponse> updateCustomerStatus(
            @Parameter(description = "Customer ID") @PathVariable("id") UUID id,
            @Parameter(description = "New status") @RequestParam CustomerStatus status) {
        log.info("Updating customer status for ID: {} to {}", id, status);
        CustomerResponse response = customerService.updateCustomerStatus(id, status);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Delete customer from the system")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "Customer ID") @PathVariable("id") UUID id) {
        log.info("Deleting customer with ID: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
