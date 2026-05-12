package com.henri.MMP.jobapplication.repository;

import com.henri.MMP.Enums.JobApplicationStatus;
import com.henri.MMP.company.model.Company;
import com.henri.MMP.jobapplication.model.JobApplication;
import com.henri.MMP.jobposting.model.JobPosting;
import com.henri.MMP.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByStudent(Student student);

    List<JobApplication> findByJobPosting(JobPosting jobPosting);

    List<JobApplication> findByApplicationStatus(JobApplicationStatus status);

    boolean existsByStudent_IdAndJobPosting_Id(Long studentId, Long jobPostingId);

    List<JobApplication> findByJobPosting_Company(Company company);
}