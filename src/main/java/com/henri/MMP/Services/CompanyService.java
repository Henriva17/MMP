package com.henri.MMP.Services;
import com.henri.MMP.Enums.Role;
import com.henri.MMP.Models.Company;
import com.henri.MMP.Models.User;
import com.henri.MMP.Repositories.CompanyRepository;
import com.henri.MMP.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository,
                          UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company createProfile(Long userId, Company request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (companyRepository.existsByCompanyId(userId)) {
            throw new IllegalStateException("Company profile already exists for this user.");
        }

        if (user.getRole() != Role.USER) {
            throw new IllegalStateException("User already has role: " + user.getRole());
        }

        user.promoteToCompany();

        Company company = new Company(
                user,
                request.getDescription(),
                request.getDomainOfActivity(),
                request.getWebsiteLink(),
                request.getCompanySize()
        );

        userRepository.save(user);
        return companyRepository.save(company);
    }

    @Transactional(readOnly = true)
    public Company getCompanyId(Long userId) {
        return companyRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + userId));
    }

    @Transactional(readOnly = true)
    public Company getUser(User user) {
        return companyRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Company profile not found."));
    }

    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Company> getVerifiedCompanies() {
        return companyRepository.findByVerified(true);
    }

    @Transactional(readOnly = true)
    public List<Company> getByDomain(String domain) {
        return companyRepository.findByDomainOfActivityIgnoreCase(domain);
    }

    public Company isVerified(Long userId) {
        Company company = getCompanyId(userId);
        company.verify();
        return companyRepository.save(company);
    }

    public Company updateDescription(Long userId, String description) {
        Company company = getCompanyId(userId);
        company.updateDescription(description);
        return companyRepository.save(company);
    }

    public Company updateWebsiteLink(Long userId, String websiteLink) {
        Company company = getCompanyId(userId);
        company.updateWebsiteLink(websiteLink);
        return companyRepository.save(company);
    }
    }
