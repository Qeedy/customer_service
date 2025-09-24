package com.projectlos.customer_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Financial information request")
public class FinancialRequest {
    
    @NotNull(message = "Monthly income is required")
    @DecimalMin(value = "0.01", message = "Monthly income must be positive")
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
    
    @Builder.Default
    @Schema(description = "Existing loans amount", example = "5000000")
    private BigDecimal existingLoans = BigDecimal.ZERO;
    
    @Builder.Default
    @Schema(description = "Existing credit cards amount", example = "2000000")
    private BigDecimal existingCreditCards = BigDecimal.ZERO;
    
    @Schema(description = "Assets value", example = "100000000")
    private BigDecimal assetsValue;
    
    @Schema(description = "Liabilities value", example = "15000000")
    private BigDecimal liabilitiesValue;
}
