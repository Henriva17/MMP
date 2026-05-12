package com.henri.MMP.company.repository;

import com.henri.MMP.Enums.VerificationStatus;
import com.henri.MMP.company.model.Company;
import com.henri.MMP.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUser(User user);
    List<Company> findByDomainOfActivityIgnoreCase(String domainOfActivity);
    List<Company> findByVerificationStatus(VerificationStatus verificationStatus);
    Optional<Company> findByUserId(Long userId);
}