package com.projectlos.customer_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.projectlos.customer_service.enums.CustomerStatus;
import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer response")
public class CustomerResponse {
    
    @Schema(description = "Customer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Customer first name", example = "John")
    private String firstName;
    
    @Schema(description = "Customer last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "Customer email", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "Customer phone number", example = "+6281234567890")
    private String phone;
    
    @Schema(description = "Customer ID number", example = "1234567890123456")
    private String idNumber;
    
    @Schema(description = "Type of ID", example = "KTP")
    private IdType idType;
    
    @Schema(description = "Customer status", example = "ACTIVE")
    private CustomerStatus status;
    
    @Schema(description = "Customer address line 1", example = "Jl. Sudirman No. 123")
    private String addressLine1;
    
    @Schema(description = "Customer address line 2", example = "Apt 456")
    private String addressLine2;
    
    @Schema(description = "Customer city", example = "Jakarta")
    private String city;
    
    @Schema(description = "Customer province", example = "DKI Jakarta")
    private String province;
    
    @Schema(description = "Customer postal code", example = "12190")
    private String postalCode;
    
    @Schema(description = "Customer country", example = "Indonesia")
    private String country;
    
    @Schema(description = "Customer date of birth", example = "1990-01-01T00:00:00")
    private LocalDateTime dateOfBirth;
    
    @Schema(description = "Customer gender", example = "MALE")
    private Gender gender;
    
    @Schema(description = "Customer marital status", example = "SINGLE")
    private MaritalStatus maritalStatus;
    
    @Schema(description = "Customer nationality", example = "Indonesian")
    private String nationality;
    
    @Schema(description = "Customer creation date", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Customer last update date", example = "2024-01-01T00:00:00")
    private LocalDateTime updatedAt;
    
    // Related data
    @Schema(description = "Employment information")
    private EmploymentResponse employment;
    
    @Schema(description = "Financial information")
    private FinancialResponse financial;
    
    @Schema(description = "Family members")
    private List<FamilyMemberResponse> familyMembers;
    
    @Schema(description = "Contact methods")
    private List<CustomerContactResponse> contacts;
}
