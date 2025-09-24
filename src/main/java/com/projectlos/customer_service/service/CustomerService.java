package com.projectlos.customer_service.service;

import com.projectlos.customer_service.dto.request.CustomerRequest;
import com.projectlos.customer_service.dto.response.CustomerResponse;
import com.projectlos.customer_service.dto.response.CustomerSummaryResponse;
import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.entity.Employment;
import com.projectlos.customer_service.entity.Financial;
import com.projectlos.customer_service.entity.FamilyMember;
import com.projectlos.customer_service.entity.CustomerContact;
import com.projectlos.customer_service.enums.CustomerStatus;
import com.projectlos.customer_service.enums.Gender;
import com.projectlos.customer_service.enums.IdType;
import com.projectlos.customer_service.enums.MaritalStatus;
import com.projectlos.customer_service.exception.CustomerNotFoundException;
import com.projectlos.customer_service.exception.InvalidCustomerException;
import com.projectlos.customer_service.exception.CustomerConflictException;
import com.projectlos.customer_service.mapper.CustomerMapper;
import com.projectlos.customer_service.repository.CustomerRepository;
import com.projectlos.customer_service.repository.EmploymentRepository;
import com.projectlos.customer_service.repository.FinancialRepository;
import com.projectlos.customer_service.repository.FamilyMemberRepository;
import com.projectlos.customer_service.repository.CustomerContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final EmploymentRepository employmentRepository;
    private final FinancialRepository financialRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final CustomerContactRepository customerContactRepository;
    private final CustomerMapper customerMapper;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating new customer with email: {}", request.getEmail());
        
        // Check if customer already exists
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerConflictException("Customer with email " + request.getEmail() + " already exists");
        }
        
        if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new CustomerConflictException("Customer with phone number " + request.getPhone() + " already exists");
        }
        
        if (customerRepository.findByIdNumber(request.getIdNumber()).isPresent()) {
            throw new CustomerConflictException("Customer with ID number " + request.getIdNumber() + " already exists");
        }
        
        Customer customer = customerMapper.toEntity(request);
        customer.setStatus(CustomerStatus.PENDING);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created successfully with ID: {}", savedCustomer.getId());
        
        // Simpan relasi secara terpisah
        saveCustomerRelations(savedCustomer, request);
        
        return customerMapper.toResponse(savedCustomer);
    }
    
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {
        log.info("Fetching customer with ID: {} using separate queries approach", id);
        
        // 1. Load basic Customer first
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        
        // 2. Load Financial separately if exists
        Financial financial = financialRepository.findByCustomer_Id(id).orElse(null);
        customer.setFinancial(financial);
        
        // 3. Load Employments separately if exists
        List<Employment> employments = employmentRepository.findByCustomer_Id(id);
        if (!employments.isEmpty()) {
            customer.setEmployments(employments);
        }
        
        // 4. Load FamilyMembers separately if exists
        List<FamilyMember> familyMembers = familyMemberRepository.findByCustomer_Id(id);
        if (!familyMembers.isEmpty()) {
            customer.setFamilyMembers(familyMembers);
        }
        
        // 5. Load Contacts separately if exists
        List<CustomerContact> contacts = customerContactRepository.findByCustomer_Id(id);
        if (!contacts.isEmpty()) {
            customer.setContacts(contacts);
        }
        
        log.info("Successfully loaded customer with separate queries - Financial: {}, Employments: {}, FamilyMembers: {}, Contacts: {}",
                financial != null ? "✓" : "✗",
                employments.size(),
                familyMembers.size(),
                contacts.size());
        
        return customerMapper.toResponse(customer);
    }
    
    
    @Transactional(readOnly = true)
    public Page<CustomerSummaryResponse> getAllCustomersSummary(Pageable pageable) {
        log.info("Fetching all customers summary with pagination");
        // Use universal search method with default values (no filters)
        Page<Customer> customers = customerRepository.searchCustomers("", "ALL", "ALL", "ALL", "ALL", pageable);
        return customers.map(customerMapper::toSummaryResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<CustomerSummaryResponse> getAllCustomersSummaryWithSearch(
            String search, 
            Gender gender, 
            IdType idType, 
            MaritalStatus maritalStatus, 
            CustomerStatus status, 
            Pageable pageable) {
        log.info("Fetching customers summary with search: {}, gender: {}, idType: {}, maritalStatus: {}, status: {}", 
                search, gender, idType, maritalStatus, status);
        
        // Convert parameters to strings with default values
        String searchParam = (search != null && !search.trim().isEmpty()) ? search.trim() : "";
        String genderParam = gender != null ? gender.name() : null;
        String idTypeParam = idType != null ? idType.name() : null ;
        String maritalStatusParam = maritalStatus != null ? maritalStatus.name() : null;
        String statusParam = status != null ? status.name() : null;
        
        Page<Customer> customers = customerRepository.searchCustomers(
                searchParam, genderParam, idTypeParam, maritalStatusParam, statusParam, pageable);
        
        return customers.map(customerMapper::toSummaryResponse);
    }
    
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        log.info("Updating customer with ID: {}", id);
        
        // Cari customer yang akan diupdate
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        
        // Validasi email unik jika berubah
        if (!existingCustomer.getEmail().equals(request.getEmail()) && 
            customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerConflictException("Customer with email " + request.getEmail() + " already exists");
        }
        
        // Validasi phone unik jika berubah
        if (!existingCustomer.getPhone().equals(request.getPhone()) && 
            customerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new CustomerConflictException("Customer with phone number " + request.getPhone() + " already exists");
        }
        
        // Validasi ID number unik jika berubah
        if (!existingCustomer.getIdNumber().equals(request.getIdNumber()) && 
            customerRepository.findByIdNumber(request.getIdNumber()).isPresent()) {
            throw new CustomerConflictException("Customer with ID number " + request.getIdNumber() + " already exists");
        }
        
        // Update data basic customer
        Customer updatedCustomer = customerMapper.toEntity(request);
        updatedCustomer.setId(existingCustomer.getId());
        updatedCustomer.setStatus(existingCustomer.getStatus());
        updatedCustomer.setCreatedAt(existingCustomer.getCreatedAt());
        updatedCustomer.setUpdatedAt(LocalDateTime.now());
        
        Customer savedCustomer = customerRepository.save(updatedCustomer);
        log.info("Customer updated successfully with ID: {}", savedCustomer.getId());
        
        // Update relasi secara terpisah
        updateCustomerRelations(savedCustomer, request);
        
        return customerMapper.toResponse(savedCustomer);
    }
    
    public CustomerResponse updateCustomerStatus(UUID id, CustomerStatus status) {
        log.info("Updating customer status for ID: {} to {}", id, status);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        
        customer.setStatus(status);
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer status updated successfully");
        
        return customerMapper.toResponse(updatedCustomer);
    }
    
    public void deleteCustomer(UUID id) {
        log.info("Deleting customer with ID: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
        log.info("Customer deleted successfully");
    }
    
    
    private void saveCustomerRelations(Customer customer, CustomerRequest request) {
        // Simpan Employment
        if (request.getEmployment() != null) {
            Employment employment = mapEmploymentRequest(request.getEmployment());
            employment.setCustomer(customer);
            employmentRepository.save(employment);
        }
        
        // Simpan Financial
        if (request.getFinancial() != null) {
            Financial financial = mapFinancialRequest(request.getFinancial());
            financial.setCustomer(customer);
            financialRepository.save(financial);
        }
        
        // Simpan Family Members
        if (request.getFamilyMembers() != null && !request.getFamilyMembers().isEmpty()) {
            List<FamilyMember> familyMembers = request.getFamilyMembers().stream()
                    .map(this::mapFamilyMemberRequest)
                    .peek(fm -> fm.setCustomer(customer))
                    .collect(Collectors.toList());
            familyMemberRepository.saveAll(familyMembers);
        }
        
        // Simpan Customer Contacts
        if (request.getContacts() != null && !request.getContacts().isEmpty()) {
            List<CustomerContact> contacts = request.getContacts().stream()
                    .map(this::mapCustomerContactRequest)
                    .peek(cc -> cc.setCustomer(customer))
                    .collect(Collectors.toList());
            customerContactRepository.saveAll(contacts);
        }
    }
    
    @Transactional
    private void updateCustomerRelations(Customer customer, CustomerRequest request) {
        UUID customerId = customer.getId();
        log.info("Updating customer relations for customer ID: {} using EXPLICIT DELETE approach", customerId);
        
        // STEP 1: EXPLICIT DELETE all existing relations
        log.debug("Deleting all existing relations for customer: {}", customerId);
        
        // Delete in correct order to avoid foreign key constraints
        customerContactRepository.deleteByCustomer_Id(customerId);
        familyMemberRepository.deleteByCustomer_Id(customerId);
        employmentRepository.deleteByCustomer_Id(customerId);
        financialRepository.deleteByCustomer_Id(customerId);
        
        // STEP 2: FLUSH to ensure all DELETEs are executed
        entityManager.flush();
        log.debug("Flushed all deletions for customer: {}", customerId);
        
        // STEP 3: CREATE and SAVE new relations with fresh IDs
        
        // 1. Create new Employment if provided
        if (request.getEmployment() != null) {
            log.debug("Creating new employment for customer: {}", customerId);
            Employment employment = mapEmploymentRequest(request.getEmployment());
            employment.setCustomer(customer); // Use passed customer object
            employmentRepository.save(employment);
        }
        
        // 2. Create new Financial if provided
        if (request.getFinancial() != null) {
            log.debug("Creating new financial for customer: {}", customerId);
            Financial financial = mapFinancialRequest(request.getFinancial());
            financial.setCustomer(customer); // Use passed customer object
            financialRepository.save(financial);
        }
        
        // 3. Create new Family Members if provided
        if (request.getFamilyMembers() != null && !request.getFamilyMembers().isEmpty()) {
            log.debug("Creating {} new family members for customer: {}", request.getFamilyMembers().size(), customerId);
            List<FamilyMember> familyMembers = request.getFamilyMembers().stream()
                    .map(this::mapFamilyMemberRequest)
                    .peek(fm -> fm.setCustomer(customer)) // Use passed customer object
                    .collect(Collectors.toList());
            familyMemberRepository.saveAll(familyMembers);
        }
        
        // 4. Create new Customer Contacts if provided
        if (request.getContacts() != null && !request.getContacts().isEmpty()) {
            log.debug("Creating {} new contacts for customer: {}", request.getContacts().size(), customerId);
            List<CustomerContact> contacts = request.getContacts().stream()
                    .map(this::mapCustomerContactRequest)
                    .peek(cc -> cc.setCustomer(customer)) // Use passed customer object
                    .collect(Collectors.toList());
            customerContactRepository.saveAll(contacts);
        }
        
        log.info("Successfully updated all customer relations with explicit delete-insert for customer: {}", customerId);
    }
    
    // Helper methods untuk mapping relasi
    private Employment mapEmploymentRequest(com.projectlos.customer_service.dto.request.EmploymentRequest request) {
        return Employment.builder()
                .companyName(request.getCompanyName())
                .position(request.getPosition())
                .employmentType(request.getEmploymentType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .monthlySalary(request.getMonthlySalary())
                .isPrimary(request.getIsPrimary())
                .build();
    }
    
    private Financial mapFinancialRequest(com.projectlos.customer_service.dto.request.FinancialRequest request) {
        return Financial.builder()
                .monthlyIncome(request.getMonthlyIncome())
                .monthlyExpenses(request.getMonthlyExpenses())
                .bankName(request.getBankName())
                .bankAccount(request.getBankAccount())
                .accountHolderName(request.getAccountHolderName())
                .creditScore(request.getCreditScore())
                .existingLoans(request.getExistingLoans())
                .existingCreditCards(request.getExistingCreditCards())
                .assetsValue(request.getAssetsValue())
                .liabilitiesValue(request.getLiabilitiesValue())
                .build();
    }
    
    private FamilyMember mapFamilyMemberRequest(com.projectlos.customer_service.dto.request.FamilyMemberRequest request) {
        return FamilyMember.builder()
                .relationship(request.getRelationship())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .occupation(request.getOccupation())
                .monthlyIncome(request.getMonthlyIncome())
                .isDependent(request.getIsDependent())
                .build();
    }
    
    private CustomerContact mapCustomerContactRequest(com.projectlos.customer_service.dto.request.CustomerContactRequest request) {
        return CustomerContact.builder()
                .contactType(request.getContactType())
                .contactValue(request.getContactValue())
                .contactLabel(request.getContactLabel())
                .isPrimary(request.getIsPrimary())
                .isPreferred(request.getIsPreferred())
                .allowNotifications(request.getAllowNotifications())
                .build();
    }
}
