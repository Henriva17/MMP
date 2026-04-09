package com.henri.MMP.Services;
import com.henri.MMP.Models.JobApplication;
import com.henri.MMP.Models.JobPosting;
import com.henri.MMP.Models.Student;
import com.henri.MMP.Repositories.JobApplicationRepository;
import com.henri.MMP.Repositories.JobPostingRepository;
import com.henri.MMP.Repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final StudentRepository studentRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository,
                                 JobPostingRepository jobPostingRepository,
                                 StudentRepository studentRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.studentRepository = studentRepository;
    }

    public JobApplication apply(Long studentId, JobApplication request) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        JobPosting posting = jobPostingRepository.findById(request.getJobPosting().getJobPostingId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Job posting not found: " + request.getJobPosting().getJobPostingId()));

        if (jobApplicationRepository.existsByStudentIdAndJobPostingId(studentId, request.getJobPosting().getJobPostingId())) {
            throw new IllegalStateException("Student has already applied to this posting.");
        }

        JobApplication application = new JobApplication(
                student,
                posting,
                request.getMotivationLetter()
        );

        return jobApplicationRepository.save(application);
    }

    public JobApplication review(Long id) {
        JobApplication app = getById(id);
        app.markAsReviewed();
        return jobApplicationRepository.save(app);
    }

    public JobApplication accept(Long id) {
        JobApplication app = getById(id);
        app.accept();
        return jobApplicationRepository.save(app);
    }

    public JobApplication reject(Long id) {
        JobApplication app = getById(id);
        app.reject();
        return jobApplicationRepository.save(app);
    }

    public JobApplication withdraw(Long id) {
        JobApplication app = getById(id);
        app.withdraw();
        return jobApplicationRepository.save(app);
    }

    @Transactional(readOnly = true)
    public List<JobApplication> getByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
        return jobApplicationRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<JobApplication> getByJobPosting(Long jobPostingId) {
        JobPosting posting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new IllegalArgumentException("Job posting not found: " + jobPostingId));
        return jobApplicationRepository.findByJobPostingId(jobPostingId);
    }

    @Transactional(readOnly = true)
    public JobApplication getById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + id));
    }
}

