package com.projectlos.customer_service.entity;

import com.projectlos.customer_service.enums.ContactType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer_contacts", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "contactType", "contactValue"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CustomerContact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contactType;
    
    @Column(nullable = false)
    private String contactValue;
    
    @Column
    private String contactLabel; // Home Phone, Work Email, etc
    
    @Builder.Default
    @Column
    private Boolean isPrimary = false;
    
    @Builder.Default
    @Column
    private Boolean isPreferred = false;
    
    @Builder.Default
    @Column
    private Boolean allowNotifications = true;
    
    @Builder.Default
    @Column
    private String status = "ACTIVE";
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
}
