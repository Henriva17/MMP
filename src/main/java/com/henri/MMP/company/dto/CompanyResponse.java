package com.henri.MMP.company.dto;

import com.henri.MMP.Enums.VerificationStatus;

public record CompanyResponse(
        Long id,
        Long userId,
        String fullName,
        String email,
        String description,
        String domainOfActivity,
        String websiteLink,
        Integer companySize,
        VerificationStatus verificationStatus
) {
}