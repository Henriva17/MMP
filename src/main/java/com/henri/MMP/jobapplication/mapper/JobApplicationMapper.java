package com.henri.MMP.jobapplication.mapper;

import com.henri.MMP.jobapplication.dto.JobApplicationResponse;
import com.henri.MMP.jobapplication.model.JobApplication;

public final class JobApplicationMapper {

    private JobApplicationMapper() {
    }

    public static JobApplicationResponse toResponse(JobApplication application) {
        return new JobApplicationResponse(
                application.getId(),
                application.getStudent().getId(),
                application.getStudent().getUser().getFullName(),
                application.getJobPosting().getId(),
                application.getJobPosting().getJobTitle(),
                application.getApplicationStatus(),
                application.getMotivationLetter(),
                application.getAppliedAt(),
                application.getUpdatedAt()
        );
    }
}
