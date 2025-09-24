package com.projectlos.customer_service.repository;

import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.entity.FamilyMember;
import com.projectlos.customer_service.enums.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {
    
    List<FamilyMember> findByCustomer(Customer customer);
    
    List<FamilyMember> findByCustomer_Id(UUID customerId);
    
    List<FamilyMember> findByCustomer_IdAndIsDependentTrue(UUID customerId);
    
    List<FamilyMember> findByCustomer_IdAndRelationship(UUID customerId, Relationship relationship);
    
    @Modifying
    @Transactional
    void deleteByCustomer_Id(UUID customerId);
}
