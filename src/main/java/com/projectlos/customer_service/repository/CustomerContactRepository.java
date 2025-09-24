package com.projectlos.customer_service.repository;

import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.entity.CustomerContact;
import com.projectlos.customer_service.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, UUID> {
    
    List<CustomerContact> findByCustomer(Customer customer);
    
    List<CustomerContact> findByCustomer_Id(UUID customerId);
    
    List<CustomerContact> findByCustomer_IdAndStatus(UUID customerId, String status);
    
    List<CustomerContact> findByCustomer_IdAndContactType(UUID customerId, ContactType contactType);
    
    Optional<CustomerContact> findByCustomer_IdAndIsPrimaryTrue(UUID customerId);
    
    List<CustomerContact> findByCustomer_IdAndIsPreferredTrue(UUID customerId);
    
    List<CustomerContact> findByCustomer_IdAndAllowNotificationsTrue(UUID customerId);
    
    @Modifying
    @Transactional
    void deleteByCustomer_Id(UUID customerId);
}
