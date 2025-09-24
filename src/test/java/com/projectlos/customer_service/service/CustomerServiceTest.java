package com.projectlos.customer_service.service;

import com.projectlos.customer_service.dto.request.CustomerRequest;
import com.projectlos.customer_service.dto.response.CustomerResponse;
import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.enums.CustomerStatus;
import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;
import com.projectlos.customer_service.mapper.CustomerMapper;
import com.projectlos.customer_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private CustomerRequest customerRequest;
    private Customer customer;
    private UUID testCustomerId;

    @BeforeEach
    void setUp() {
        testCustomerId = UUID.randomUUID();
        
        customerRequest = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+6281234567890")
                .idNumber("1234567890123456")
                .idType(IdType.KTP)
                .addressLine1("Jl. Sudirman No. 123")
                .city("Jakarta")
                .province("DKI Jakarta")
                .postalCode("12190")
                .dateOfBirth(LocalDateTime.now().minusYears(30))
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .build();

        customer = Customer.builder()
                .id(testCustomerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+6281234567890")
                .idNumber("1234567890123456")
                .idType(IdType.KTP)
                .status(CustomerStatus.PENDING)
                .addressLine1("Jl. Sudirman No. 123")
                .city("Jakarta")
                .province("DKI Jakarta")
                .postalCode("12190")
                .dateOfBirth(LocalDateTime.now().minusYears(30))
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createCustomer_Success() {
        // Given
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(customerRepository.findByIdNumber(anyString())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(any(CustomerRequest.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponse(any(Customer.class))).thenReturn(
            CustomerResponse.builder()
                .id(testCustomerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+6281234567890")
                .idNumber("1234567890123456")
                .idType(IdType.KTP)
                .status(CustomerStatus.PENDING)
                .addressLine1("Jl. Sudirman No. 123")
                .city("Jakarta")
                .province("DKI Jakarta")
                .postalCode("12190")
                .dateOfBirth(LocalDateTime.now().minusYears(30))
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        );

        // When
        var result = customerService.createCustomer(customerRequest);

        // Then
        assertNotNull(result);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_EmailAlreadyExists_ThrowsException() {
        // Given
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));

        // When & Then
        assertThrows(RuntimeException.class, () -> customerService.createCustomer(customerRequest));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void getCustomerById_Success() {
        // Given
        when(customerRepository.findById(testCustomerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponse(any(Customer.class))).thenReturn(
            CustomerResponse.builder()
                .id(testCustomerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+6281234567890")
                .idNumber("1234567890123456")
                .idType(IdType.KTP)
                .status(CustomerStatus.PENDING)
                .addressLine1("Jl. Sudirman No. 123")
                .city("Jakarta")
                .province("DKI Jakarta")
                .postalCode("12190")
                .dateOfBirth(LocalDateTime.now().minusYears(30))
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        );

        // When
        var result = customerService.getCustomerById(testCustomerId);

        // Then
        assertNotNull(result);
        verify(customerRepository, times(1)).findById(testCustomerId);
    }

    @Test
    void getCustomerById_NotFound_ThrowsException() {
        // Given
        when(customerRepository.findById(testCustomerId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(testCustomerId));
    }
}
