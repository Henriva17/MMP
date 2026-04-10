package com.henri.MMP.jobposting.mapper;

import com.henri.MMP.jobposting.dto.JobPostingResponse;
import com.henri.MMP.jobposting.model.JobPosting;

public final class JobPostingMapper {

    private JobPostingMapper() {
    }

    public static JobPostingResponse toResponse(JobPosting posting) {
        return new JobPostingResponse(
                posting.getId(),
                posting.getCompany().getId(),
                posting.getCompany().getUser().getFullName(),
                posting.getJobTitle(),
                posting.getDescription(),
                posting.getJobType(),
                posting.getWorkMode(),
                posting.getLocation(),
                posting.getRequiredSkills(),
                posting.getRequiredEducationLevel(),
                posting.getApplicationDeadline(),
                posting.getJobPostingStatus(),
                posting.getCreatedAt(),
                posting.getUpdatedAt()

                /*
                 Company company,
            String jobTitle,
            String description,
            JobType jobType,
            WorkMode workMode,
            String location,
            EducationLevel requiredEducationLevel,
            LocalDate applicationDeadline*/
        );
    }
}
