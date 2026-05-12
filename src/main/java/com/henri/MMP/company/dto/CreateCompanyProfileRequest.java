package com.henri.MMP.company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCompanyProfileRequest(
        String description,

        @NotBlank(message = "Domain of activity is required")
        String domainOfActivity,

        String websiteLink,

        @NotNull(message = "Company size is required")
        @Positive(message = "Company size must be greater than 0")
        Integer companySize
) {
}