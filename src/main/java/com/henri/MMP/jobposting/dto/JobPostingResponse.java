package com.henri.MMP.jobposting.dto;

import com.henri.MMP.Enums.EducationLevel;
import com.henri.MMP.Enums.JobPostingStatus;
import com.henri.MMP.Enums.JobType;
import com.henri.MMP.Enums.WorkMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record JobPostingResponse(
        Long id,
        Long companyId,
        String companyName,
        String jobTitle,
        String description,
        JobType jobType,
        WorkMode workMode,
        String location,
        Set<String> requiredSkills,
        EducationLevel requiredEducationLevel,
        LocalDate applicationDeadline,
        JobPostingStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
