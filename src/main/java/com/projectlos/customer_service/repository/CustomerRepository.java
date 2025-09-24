package com.projectlos.customer_service.repository;

import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    
    Optional<Customer> findByEmail(String email);
    
    Optional<Customer> findByPhone(String phone);
    
    Optional<Customer> findByIdNumber(String idNumber);
    
    List<Customer> findByStatus(CustomerStatus status);
    
    @Query("SELECT c FROM Customer c WHERE c.firstName ILIKE %:name% OR c.lastName ILIKE %:name%")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT c FROM Customer c WHERE c.city = :city")
    List<Customer> findByCity(@Param("city") String city);
    
    @Query("SELECT c FROM Customer c WHERE " +
           "(:search = '' OR " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.idNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.city) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.province) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:gender IS NULL OR c.gender = :gender) AND " +
           "(:idType IS NULL OR c.idType = :idType) AND " +
           "(:maritalStatus IS NULL OR c.maritalStatus = :maritalStatus) AND " +
           "(:status IS NULL OR c.status = :status)")
    Page<Customer> searchCustomers(@Param("search") String search,
                                 @Param("gender") String gender,
                                 @Param("idType") String idType,
                                 @Param("maritalStatus") String maritalStatus,
                                 @Param("status") String status,
                                 Pageable pageable);
    
    // Simple approach - no EntityGraph, just basic findById
    
}
