package com.henri.MMP.jobposting.dto;

import com.henri.MMP.Enums.EducationLevel;
import com.henri.MMP.Enums.JobType;
import com.henri.MMP.Enums.WorkMode;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateJobPostingRequest(
        @NotBlank(message = "Job title is required")
        String jobTitle,

        String description,

        @NotNull(message = "Job type is required")
        JobType jobType,

        @NotNull(message = "Work mode is required")
        WorkMode workMode,

        String location,

        Set<String> requiredSkills,

        @NotNull(message = "Required education level is required")
        EducationLevel requiredEducationLevel,

        @NotNull(message = "Application deadline is required")
        @Future(message = "Application deadline must be in the future")
        LocalDate applicationDeadline
) {
}