package com.projectlos.customer_service.repository;

import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, UUID> {
    
    Optional<Financial> findByCustomer(Customer customer);
    
    Optional<Financial> findByCustomer_Id(UUID customerId);
    
    boolean existsByCustomer_Id(UUID customerId);
    
    @Modifying
    @Transactional
    void deleteByCustomer_Id(UUID customerId);
}
