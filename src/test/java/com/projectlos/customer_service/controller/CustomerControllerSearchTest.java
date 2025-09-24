package com.projectlos.customer_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlos.customer_service.enums.CustomerStatus;
import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;
import com.projectlos.customer_service.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerSearchTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCustomersWithSearch() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<com.projectlos.customer_service.dto.response.CustomerSummaryResponse> mockPage = 
            new PageImpl<>(Collections.emptyList(), pageable, 0);
        
        when(customerService.getAllCustomersSummaryWithSearch(
            anyString(), any(Gender.class), any(IdType.class), 
            any(MaritalStatus.class), any(CustomerStatus.class), any(Pageable.class)))
            .thenReturn(mockPage);

        // When & Then
        mockMvc.perform(get("/api/customers")
                .param("search", "john")
                .param("gender", "MALE")
                .param("idType", "KTP")
                .param("maritalStatus", "SINGLE")
                .param("status", "ACTIVE")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCustomersWithoutFilters() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<com.projectlos.customer_service.dto.response.CustomerSummaryResponse> mockPage = 
            new PageImpl<>(Collections.emptyList(), pageable, 0);
        
        when(customerService.getAllCustomersSummary(any(Pageable.class)))
            .thenReturn(mockPage);

        // When & Then
        mockMvc.perform(get("/api/customers")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }
}
