package com.henri.MMP.jobposting.repository;

import com.henri.MMP.Enums.JobPostingStatus;
import com.henri.MMP.Enums.JobType;
import com.henri.MMP.Enums.WorkMode;
import com.henri.MMP.jobposting.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByJobPostingStatus(JobPostingStatus status);

    List<JobPosting> findByCompany_Id(Long companyId);

    List<JobPosting> findByJobType(JobType jobType);

    List<JobPosting> findByWorkMode(WorkMode workMode);

    List<JobPosting> findByJobPostingStatusAndWorkMode(JobPostingStatus status, WorkMode workMode);

    List<JobPosting> findByCompany_IdAndJobPostingStatus(Long companyId, JobPostingStatus status);

    List<JobPosting> findByJobPostingStatusAndApplicationDeadlineAfter(JobPostingStatus status, LocalDate date);
}
