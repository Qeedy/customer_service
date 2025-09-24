package com.projectlos.customer_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "financials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Financial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column
    private BigDecimal monthlyIncome;
    
    @Column
    private BigDecimal monthlyExpenses;
    
    @Column
    private String bankName;
    
    @Column
    private String bankAccount;
    
    @Column
    private String accountHolderName;
    
    @Column
    private Integer creditScore;
    
    @Builder.Default
    @Column
    private BigDecimal existingLoans = BigDecimal.ZERO;
    
    @Builder.Default
    @Column
    private BigDecimal existingCreditCards = BigDecimal.ZERO;
    
    @Column
    private BigDecimal assetsValue;
    
    @Column
    private BigDecimal liabilitiesValue;
    
    @Column
    private BigDecimal netWorth;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
