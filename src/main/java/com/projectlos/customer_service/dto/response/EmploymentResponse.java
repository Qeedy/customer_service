package com.projectlos.customer_service.dto.response;

import com.projectlos.customer_service.enums.EmploymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employment information response")
public class EmploymentResponse {
    
    @Schema(description = "Employment ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Company name", example = "Tech Corp")
    private String companyName;
    
    @Schema(description = "Position", example = "Software Engineer")
    private String position;
    
    @Schema(description = "Employment type", example = "PERMANENT")
    private EmploymentType employmentType;
    
    @Schema(description = "Employment start date", example = "2020-01-01")
    private LocalDate startDate;
    
    @Schema(description = "Employment end date", example = "2025-01-01")
    private LocalDate endDate;
    
    @Schema(description = "Monthly salary", example = "15000000")
    private BigDecimal monthlySalary;
    
    @Schema(description = "Employment status", example = "ACTIVE")
    private String employmentStatus;
    
    @Schema(description = "Is primary employment", example = "true")
    private Boolean isPrimary;
    
    @Schema(description = "Creation date", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
}
