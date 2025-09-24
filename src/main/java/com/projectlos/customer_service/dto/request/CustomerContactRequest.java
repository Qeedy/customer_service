package com.projectlos.customer_service.dto.request;

import com.projectlos.customer_service.enums.ContactType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer contact information request")
public class CustomerContactRequest {
    
    @NotNull(message = "Contact type is required")
    @Schema(description = "Contact type", example = "EMAIL")
    private ContactType contactType;
    
    @NotBlank(message = "Contact value is required")
    @Schema(description = "Contact value", example = "john.doe@email.com")
    private String contactValue;
    
    @Schema(description = "Contact label", example = "Work Email")
    private String contactLabel;
    
    @Builder.Default
    @Schema(description = "Is primary contact", example = "false")
    private Boolean isPrimary = false;
    
    @Builder.Default
    @Schema(description = "Is preferred contact", example = "true")
    private Boolean isPreferred = false;
    
    @Builder.Default
    @Schema(description = "Allow notifications", example = "true")
    private Boolean allowNotifications = true;
}
