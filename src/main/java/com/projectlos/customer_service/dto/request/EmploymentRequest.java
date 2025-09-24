package com.projectlos.customer_service.dto.request;

import com.projectlos.customer_service.enums.EmploymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employment information request")
public class EmploymentRequest {
    
    @NotBlank(message = "Company name is required")
    @Schema(description = "Company name", example = "Tech Corp")
    private String companyName;
    
    @Schema(description = "Position", example = "Software Engineer")
    private String position;
    
    @NotNull(message = "Employment type is required")
    @Schema(description = "Employment type", example = "PERMANENT")
    private EmploymentType employmentType;
    
    @NotNull(message = "Start date is required")
    @Schema(description = "Employment start date", example = "2020-01-01")
    private LocalDate startDate;
    
    @Schema(description = "Employment end date", example = "2025-01-01")
    private LocalDate endDate;
    
    @NotNull(message = "Monthly salary is required")
    @DecimalMin(value = "0.01", message = "Monthly salary must be positive")
    @Schema(description = "Monthly salary", example = "15000000")
    private BigDecimal monthlySalary;
    
    @Builder.Default
    @Schema(description = "Is primary employment", example = "true")
    private Boolean isPrimary = true;
}
