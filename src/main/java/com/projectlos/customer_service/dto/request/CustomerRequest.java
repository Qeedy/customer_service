package com.projectlos.customer_service.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer registration request")
public class CustomerRequest {
    
    @NotBlank(message = "First name is required")
    @Schema(description = "Customer first name", example = "John")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Schema(description = "Customer last name", example = "Doe")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Customer email", example = "john.doe@example.com")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "Invalid phone number format")
    @Schema(description = "Customer phone number", example = "+6281234567890")
    private String phone;
    
    @NotBlank(message = "ID number is required")
    @Schema(description = "Customer ID number", example = "1234567890123456")
    private String idNumber;
    
    @NotNull(message = "ID type is required")
    @Schema(description = "Type of ID", example = "KTP")
    private IdType idType;
    
    @NotBlank(message = "Address line 1 is required")
    @Schema(description = "Customer address line 1", example = "Jl. Sudirman No. 123")
    private String addressLine1;
    
    @Schema(description = "Customer address line 2", example = "Apt 456")
    private String addressLine2;
    
    @NotBlank(message = "City is required")
    @Schema(description = "Customer city", example = "Jakarta")
    private String city;
    
    @NotBlank(message = "Province is required")
    @Schema(description = "Customer province", example = "DKI Jakarta")
    private String province;
    
    @NotBlank(message = "Postal code is required")
    @Schema(description = "Customer postal code", example = "12190")
    private String postalCode;
    
    @Builder.Default
    @Schema(description = "Customer country", example = "Indonesia")
    private String country = "Indonesia";
    
    @NotNull(message = "Date of birth is required")
    @Schema(description = "Customer date of birth", example = "1990-01-01T00:00:00")
    private LocalDate dateOfBirth;
    
    @NotNull(message = "Gender is required")
    @Schema(description = "Customer gender", example = "MALE")
    private Gender gender;
    
    @NotNull(message = "Marital status is required")
    @Schema(description = "Customer marital status", example = "SINGLE")
    private MaritalStatus maritalStatus;
    
    @Builder.Default
    @Schema(description = "Customer nationality", example = "Indonesian")
    private String nationality = "Indonesian";
    
    // Employment information
    @Valid
    @Schema(description = "Employment information")
    private EmploymentRequest employment;
    
    // Financial information
    @Valid
    @Schema(description = "Financial information")
    private FinancialRequest financial;
    
    // Family members
    @Valid
    @Schema(description = "Family members")
    private List<FamilyMemberRequest> familyMembers;
    
    // Contact information
    @Valid
    @Schema(description = "Additional contact methods")
    private List<CustomerContactRequest> contacts;
}
