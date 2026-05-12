package com.henri.MMP.jobapplication.service;

<<<<<<< HEAD
=======
import com.henri.MMP.company.model.Company;
import com.henri.MMP.company.repository.CompanyRepository;
>>>>>>> henridev
import com.henri.MMP.jobapplication.dto.ApplyToJobRequest;
import com.henri.MMP.jobapplication.dto.JobApplicationResponse;
import com.henri.MMP.jobapplication.mapper.JobApplicationMapper;
import com.henri.MMP.jobapplication.model.JobApplication;
import com.henri.MMP.jobapplication.repository.JobApplicationRepository;
import com.henri.MMP.jobposting.model.JobPosting;
import com.henri.MMP.jobposting.repository.JobPostingRepository;
import com.henri.MMP.student.model.Student;
import com.henri.MMP.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final StudentRepository studentRepository;
<<<<<<< HEAD
=======
    private final CompanyRepository companyRepository;
>>>>>>> henridev

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            JobPostingRepository jobPostingRepository,
<<<<<<< HEAD
            StudentRepository studentRepository
=======
            StudentRepository studentRepository,
            CompanyRepository companyRepository
>>>>>>> henridev
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.studentRepository = studentRepository;
<<<<<<< HEAD
=======
        this.companyRepository = companyRepository;
>>>>>>> henridev
    }

    public JobApplicationResponse apply(Long studentId, ApplyToJobRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        JobPosting posting = jobPostingRepository.findById(request.jobPostingId())
                .orElseThrow(() -> new IllegalArgumentException("Job posting not found: " + request.jobPostingId()));

        if (jobApplicationRepository.existsByStudent_IdAndJobPosting_Id(studentId, request.jobPostingId())) {
            throw new IllegalStateException("Student has already applied to this posting.");
        }

        JobApplication application = new JobApplication(
                student,
                posting,
                request.motivationLetter()
        );

        return JobApplicationMapper.toResponse(jobApplicationRepository.save(application));
    }

    public JobApplicationResponse review(Long id) {
        JobApplication application = findApplicationEntityById(id);
        application.markAsReviewed();
        return JobApplicationMapper.toResponse(jobApplicationRepository.save(application));
    }

    public JobApplicationResponse accept(Long id) {
        JobApplication application = findApplicationEntityById(id);
        application.accept();
        return JobApplicationMapper.toResponse(jobApplicationRepository.save(application));
    }

    public JobApplicationResponse reject(Long id) {
        JobApplication application = findApplicationEntityById(id);
        application.reject();
        return JobApplicationMapper.toResponse(jobApplicationRepository.save(application));
    }

    public JobApplicationResponse withdraw(Long id) {
        JobApplication application = findApplicationEntityById(id);
        application.withdraw();
        return JobApplicationMapper.toResponse(jobApplicationRepository.save(application));
    }

    @Transactional(readOnly = true)
    public List<JobApplicationResponse> getByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        return jobApplicationRepository.findByStudent(student)
                .stream()
                .map(JobApplicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobApplicationResponse> getByJobPosting(Long jobPostingId) {
        JobPosting posting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new IllegalArgumentException("Job posting not found: " + jobPostingId));

        return jobApplicationRepository.findByJobPosting(posting)
                .stream()
                .map(JobApplicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
<<<<<<< HEAD
=======
    public List<JobApplicationResponse> getByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + companyId));

        return jobApplicationRepository.findByJobPosting_Company(company)
                .stream()
                .map(JobApplicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
>>>>>>> henridev
    public JobApplicationResponse getById(Long id) {
        return JobApplicationMapper.toResponse(findApplicationEntityById(id));
    }

    protected JobApplication findApplicationEntityById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + id));
    }
}