package com.projectlos.customer_service.repository;

import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.entity.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, UUID> {
    
    List<Employment> findByCustomer(Customer customer);
    
    List<Employment> findByCustomer_Id(UUID customerId);
    
    List<Employment> findByCustomer_IdAndEmploymentStatus(UUID customerId, String employmentStatus);
    
    List<Employment> findByCustomer_IdAndIsPrimaryTrue(UUID customerId);
    
    boolean existsByCustomer_IdAndIsPrimaryTrue(UUID customerId);
    
    @Modifying
    @Transactional
    void deleteByCustomer_Id(UUID customerId);
}
