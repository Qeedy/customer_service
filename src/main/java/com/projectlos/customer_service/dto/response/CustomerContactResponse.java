package com.projectlos.customer_service.dto.response;

import com.projectlos.customer_service.enums.ContactType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer contact information response")
public class CustomerContactResponse {
    
    @Schema(description = "Contact ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Contact type", example = "EMAIL")
    private ContactType contactType;
    
    @Schema(description = "Contact value", example = "john.doe@email.com")
    private String contactValue;
    
    @Schema(description = "Contact label", example = "Work Email")
    private String contactLabel;
    
    @Schema(description = "Is primary contact", example = "false")
    private Boolean isPrimary;
    
    @Schema(description = "Is preferred contact", example = "true")
    private Boolean isPreferred;
    
    @Schema(description = "Allow notifications", example = "true")
    private Boolean allowNotifications;
    
    @Schema(description = "Contact status", example = "ACTIVE")
    private String status;
    
    @Schema(description = "Creation date", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update date", example = "2024-01-01T00:00:00")
    private LocalDateTime updatedAt;
}
