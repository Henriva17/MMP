package com.henri.MMP.jobapplication.dto;

import com.henri.MMP.Enums.JobApplicationStatus;

import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long id,
        Long studentId,
        String studentName,
        Long jobPostingId,
        String jobTitle,
        String companyName,
        JobApplicationStatus status,
        String motivationLetter,
        LocalDateTime appliedAt,
        LocalDateTime updatedAt
) {
}