package com.henri.MMP.jobapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ApplyToJobRequest(
        @NotNull(message = "Job posting id is required")
        Long jobPostingId,

        @NotBlank(message = "Motivation letter is required")
        @Size(min = 50, max = 3000, message = "Motivation letter must be between 50 and 3000 characters")
        String motivationLetter
) {
}