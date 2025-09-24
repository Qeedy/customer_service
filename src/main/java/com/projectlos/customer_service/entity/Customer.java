package com.projectlos.customer_service.entity;

import com.projectlos.customer_service.enums.CustomerStatus;
import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true)
    private String email;
    
    @Column
    private String phone;
    
    @Column
    private LocalDateTime dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    @Column
    private MaritalStatus maritalStatus;
    
    @Column
    private String nationality;
    
    @Enumerated(EnumType.STRING)
    @Column
    private IdType idType;
    
    @Column(unique = true)
    private String idNumber;
    
    @Column
    private String addressLine1;
    
    @Column
    private String addressLine2;
    
    @Column
    private String city;
    
    @Column
    private String province;
    
    @Column
    private String postalCode;
    
    @Builder.Default
    @Column
    private String country = "Indonesia";
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column
    private CustomerStatus status = CustomerStatus.ACTIVE;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<CustomerContact> contacts = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Employment> employments = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<FamilyMember> familyMembers = new ArrayList<>();
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Financial financial;
}