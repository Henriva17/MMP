package com.henri.MMP.Repositories;

import com.henri.MMP.Enums.JobApplicationStatus;
import com.henri.MMP.Models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByStudentId(Long studentId);

    List<JobApplication> findByJobPostingId(Long jobPostingId);

    List<JobApplication> findByApplicationStatus(JobApplicationStatus status);

    boolean existsByStudentIdAndJobPostingId(Long studentId, Long jobPostingId);
}
