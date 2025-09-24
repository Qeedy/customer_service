package com.projectlos.customer_service.dto.request;

import com.projectlos.customer_service.enums.Relationship;
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
@Schema(description = "Family member information request")
public class FamilyMemberRequest {
    
    @NotNull(message = "Relationship is required")
    @Schema(description = "Relationship to customer", example = "SPOUSE")
    private Relationship relationship;
    
    @NotBlank(message = "First name is required")
    @Schema(description = "Family member first name", example = "Jane")
    private String firstName;
    
    @Schema(description = "Family member last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "Family member date of birth", example = "1992-05-15")
    private LocalDate dateOfBirth;
    
    @Schema(description = "Family member occupation", example = "Teacher")
    private String occupation;
    
    @Schema(description = "Family member monthly income", example = "8000000")
    private BigDecimal monthlyIncome;
    
    @Builder.Default
    @Schema(description = "Is dependent", example = "false")
    private Boolean isDependent = false;
}
