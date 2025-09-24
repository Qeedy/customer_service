package com.projectlos.customer_service.mapper;

import com.projectlos.customer_service.dto.request.CustomerRequest;
import com.projectlos.customer_service.dto.response.CustomerResponse;
import com.projectlos.customer_service.dto.response.CustomerSummaryResponse;
import com.projectlos.customer_service.entity.Customer;
import com.projectlos.customer_service.entity.Employment;
import com.projectlos.customer_service.entity.Financial;
import com.projectlos.customer_service.entity.FamilyMember;
import com.projectlos.customer_service.entity.CustomerContact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    
    public Customer toEntity(CustomerRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .idNumber(request.getIdNumber())
                .idType(request.getIdType())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .province(request.getProvince())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .maritalStatus(request.getMaritalStatus())
                .nationality(request.getNationality())
                .build();
        
        // Relasi akan disimpan secara terpisah oleh service layer
        // Customer entity hanya menyimpan data basic customer
        
        return customer;
    }
    
    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .idNumber(customer.getIdNumber())
                .idType(customer.getIdType())
                .status(customer.getStatus())
                .addressLine1(customer.getAddressLine1())
                .addressLine2(customer.getAddressLine2())
                .city(customer.getCity())
                .province(customer.getProvince())
                .postalCode(customer.getPostalCode())
                .country(customer.getCountry())
                .dateOfBirth(customer.getDateOfBirth())
                .gender(customer.getGender())
                .maritalStatus(customer.getMaritalStatus())
                .nationality(customer.getNationality())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                // Map relasi jika ada
                .employment(customer.getEmployments() != null && !customer.getEmployments().isEmpty() 
                    ? customer.getEmployments().stream()
                        .filter(emp -> emp.getIsPrimary())
                        .findFirst()
                        .map(this::mapEmploymentResponse)
                        .orElse(customer.getEmployments().stream()
                            .findFirst()
                            .map(this::mapEmploymentResponse)
                            .orElse(null))
                    : null)
                .financial(customer.getFinancial() != null 
                    ? mapFinancialResponse(customer.getFinancial()) 
                    : null)
                .familyMembers(customer.getFamilyMembers() != null && !customer.getFamilyMembers().isEmpty()
                    ? customer.getFamilyMembers().stream()
                        .map(this::mapFamilyMemberResponse)
                        .collect(Collectors.toList())
                    : null)
                .contacts(customer.getContacts() != null && !customer.getContacts().isEmpty()
                    ? customer.getContacts().stream()
                        .map(this::mapCustomerContactResponse)
                        .collect(Collectors.toList())
                    : null)
                .build();
    }
    
    public CustomerSummaryResponse toSummaryResponse(Customer customer) {
        return CustomerSummaryResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .city(customer.getCity())
                .createdAt(customer.getCreatedAt())
                .build();
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
    
    // Helper methods untuk mapping response
    private com.projectlos.customer_service.dto.response.EmploymentResponse mapEmploymentResponse(Employment employment) {
        return com.projectlos.customer_service.dto.response.EmploymentResponse.builder()
                .id(employment.getId())
                .companyName(employment.getCompanyName())
                .position(employment.getPosition())
                .employmentType(employment.getEmploymentType())
                .startDate(employment.getStartDate())
                .endDate(employment.getEndDate())
                .monthlySalary(employment.getMonthlySalary())
                .employmentStatus(employment.getEmploymentStatus())
                .isPrimary(employment.getIsPrimary())
                .createdAt(employment.getCreatedAt())
                .build();
    }
    
    private com.projectlos.customer_service.dto.response.FinancialResponse mapFinancialResponse(Financial financial) {
        return com.projectlos.customer_service.dto.response.FinancialResponse.builder()
                .id(financial.getId())
                .monthlyIncome(financial.getMonthlyIncome())
                .monthlyExpenses(financial.getMonthlyExpenses())
                .bankName(financial.getBankName())
                .bankAccount(financial.getBankAccount())
                .accountHolderName(financial.getAccountHolderName())
                .creditScore(financial.getCreditScore())
                .existingLoans(financial.getExistingLoans())
                .existingCreditCards(financial.getExistingCreditCards())
                .assetsValue(financial.getAssetsValue())
                .liabilitiesValue(financial.getLiabilitiesValue())
                .netWorth(financial.getNetWorth())
                .createdAt(financial.getCreatedAt())
                .updatedAt(financial.getUpdatedAt())
                .build();
    }
    
    private com.projectlos.customer_service.dto.response.FamilyMemberResponse mapFamilyMemberResponse(FamilyMember familyMember) {
        return com.projectlos.customer_service.dto.response.FamilyMemberResponse.builder()
                .id(familyMember.getId())
                .relationship(familyMember.getRelationship())
                .firstName(familyMember.getFirstName())
                .lastName(familyMember.getLastName())
                .dateOfBirth(familyMember.getDateOfBirth())
                .occupation(familyMember.getOccupation())
                .monthlyIncome(familyMember.getMonthlyIncome())
                .isDependent(familyMember.getIsDependent())
                .createdAt(familyMember.getCreatedAt())
                .build();
    }
    
    private com.projectlos.customer_service.dto.response.CustomerContactResponse mapCustomerContactResponse(CustomerContact customerContact) {
        return com.projectlos.customer_service.dto.response.CustomerContactResponse.builder()
                .id(customerContact.getId())
                .contactType(customerContact.getContactType())
                .contactValue(customerContact.getContactValue())
                .contactLabel(customerContact.getContactLabel())
                .isPrimary(customerContact.getIsPrimary())
                .isPreferred(customerContact.getIsPreferred())
                .allowNotifications(customerContact.getAllowNotifications())
                .status(customerContact.getStatus())
                .createdAt(customerContact.getCreatedAt())
                .updatedAt(customerContact.getUpdatedAt())
                .build();
    }
}
