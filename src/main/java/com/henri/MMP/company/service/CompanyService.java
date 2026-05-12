package com.henri.MMP.company.service;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.VerificationStatus;
import com.henri.MMP.company.dto.CompanyResponse;
import com.henri.MMP.company.dto.CreateCompanyProfileRequest;
import com.henri.MMP.company.mapper.CompanyMapper;
import com.henri.MMP.company.model.Company;
import com.henri.MMP.company.repository.CompanyRepository;
import com.henri.MMP.user.model.User;
import com.henri.MMP.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public CompanyResponse createProfile(Long userId, CreateCompanyProfileRequest request) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (companyRepository.existsById(userId)) {
            throw new IllegalStateException("Company profile already exists for this user.");
        }

        if (user.getRole() != Role.USER) {
            throw new IllegalStateException("User already has role: " + user.getRole());
        }

        user.promoteToCompany();

        Company company = new Company(
                user,
                request.description(),
                request.domainOfActivity(),
                request.websiteLink(),
                request.companySize()
        );

        userRepository.save(user);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public CompanyResponse getById(Long id) {
        return CompanyMapper.toResponse(findCompanyEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(CompanyMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getVerifiedCompanies() {
        return companyRepository.findByVerificationStatus(VerificationStatus.VERIFIED)
                .stream()
                .map(CompanyMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getByDomain(String domain) {
        return companyRepository.findByDomainOfActivityIgnoreCase(domain)
                .stream()
                .map(CompanyMapper::toResponse)
                .toList();
    }

    public CompanyResponse verifyCompany(Long id) {
        Company company = findCompanyEntityById(id);
        company.verify();
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    public CompanyResponse updateDescription(Long id, String description) {
        Company company = findCompanyEntityById(id);
        company.updateDescription(description);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    public CompanyResponse updateWebsiteLink(Long id, String websiteLink) {
        Company company = findCompanyEntityById(id);
        company.updateWebsiteLink(websiteLink);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    public void deleteProfile(Long id) {
        Company company = findCompanyEntityById(id);
        User user = company.getUser();

        companyRepository.delete(company);
        user.resetRoleToUser();
        userRepository.save(user);
    }

    protected Company findCompanyEntityById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));
    }
}
