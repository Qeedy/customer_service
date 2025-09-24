package com.projectlos.customer_service.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.projectlos.customer_service.enums.CustomerStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer summary response for listing")
public class CustomerSummaryResponse {
    
    @Schema(description = "Customer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Customer full name", example = "John Doe")
    private String fullName;
    
    @Schema(description = "Customer email", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "Customer phone number", example = "+6281234567890")
    private String phone;
    
    @Schema(description = "Customer status", example = "ACTIVE")
    private CustomerStatus status;
    
    @Schema(description = "Customer city", example = "Jakarta")
    private String city;
    
    @Schema(description = "Customer creation date", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
}
