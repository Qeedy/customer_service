package com.projectlos.customer_service.dto.response;

import com.projectlos.customer_service.enums.Relationship;
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
@Schema(description = "Family member information response")
public class FamilyMemberResponse {
    
    @Schema(description = "Family member ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Relationship to customer", example = "SPOUSE")
    private Relationship relationship;
    
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
    
    @Schema(description = "Is dependent", example = "false")
    private Boolean isDependent;
    
    @Schema(description = "Creation date", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
}
