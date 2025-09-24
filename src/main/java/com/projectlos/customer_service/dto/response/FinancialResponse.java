package com.projectlos.customer_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Financial information response")
public class FinancialResponse {
    
    @Schema(description = "Financial ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Monthly income", example = "15000000")
    private BigDecimal monthlyIncome;
    
    @Schema(description = "Monthly expenses", example = "8000000")
    private BigDecimal monthlyExpenses;
    
    @Schema(description = "Bank name", example = "BCA")
    private String bankName;
    
    @Schema(description = "Bank account number", example = "1234567890")
    private String bankAccount;
    
    @Schema(description = "Account holder name", example = "John Doe")
    private String accountHolderName;
    
    @Schema(description = "Credit score", example = "750")
    private Integer creditScore;
    
    @Schema(description = "Existing loans amount", example = "5000000")
    private BigDecimal existingLoans;
    
    @Schema(description = "Existing credit cards amount", example = "2000000")
    private BigDecimal existingCreditCards;
    
    @Schema(description = "Assets value", example = "100000000")
    private BigDecimal assetsValue;
    
    @Schema(description = "Liabilities value", example = "15000000")
    private BigDecimal liabilitiesValue;
    
    @Schema(description = "Net worth", example = "85000000")
    private BigDecimal netWorth;
    
    @Schema(description = "Creation date", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update date", example = "2024-01-01T00:00:00")
    private LocalDateTime updatedAt;
}
