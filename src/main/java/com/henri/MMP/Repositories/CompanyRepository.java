package com.henri.MMP.Repositories;

import com.henri.MMP.Models.Company;
import com.henri.MMP.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyId(Long companyId);

    boolean existsByCompanyId(Long companyId);

    List<Company> findByDomainOfActivityIgnoreCase(String domainOfActivity);

    Optional<Company> findByUser(User user);

    List<Company> findByVerified(boolean verified);

}
