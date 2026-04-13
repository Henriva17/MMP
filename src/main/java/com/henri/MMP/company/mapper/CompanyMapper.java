package com.henri.MMP.company.mapper;

import com.henri.MMP.company.dto.CompanyResponse;
import com.henri.MMP.company.model.Company;

public final class CompanyMapper {

    private CompanyMapper() {
    }

    public static CompanyResponse toResponse(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getUser().getId(),
                company.getUser().getFullName(),
                company.getUser().getEmail(),
                company.getDescription(),
                company.getDomainOfActivity(),
                company.getWebsiteLink(),
                company.getCompanySize(),
                company.getVerificationStatus()
        );
    }
}
