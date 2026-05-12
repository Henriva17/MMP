package com.henri.MMP.jobapplication.model;

import com.henri.MMP.Enums.JobApplicationStatus;
import com.henri.MMP.jobposting.model.JobPosting;
import com.henri.MMP.student.model.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "job_applications",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "job_posting_id"})
        }
)
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(nullable = false, length = 3000)
    private String motivationLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobApplicationStatus applicationStatus = JobApplicationStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected JobApplication() {
    }

    public JobApplication(Student student, JobPosting jobPosting, String motivationLetter) {
        if (student == null) {
            throw new IllegalArgumentException("Student is required");
        }
        if (jobPosting == null) {
            throw new IllegalArgumentException("Job posting is required");
        }
        if (motivationLetter == null || motivationLetter.trim().length() < 50) {
            throw new IllegalArgumentException("Motivation letter must be at least 50 characters.");
        }
        if (!jobPosting.isOpen()) {
            throw new IllegalStateException("Cannot apply to a closed or expired posting.");
        }

        this.student = student;
        this.jobPosting = jobPosting;
        this.motivationLetter = motivationLetter.trim();
        this.applicationStatus = JobApplicationStatus.PENDING;
    }

    public void markAsReviewed() {
        if (this.applicationStatus != JobApplicationStatus.PENDING) {
            throw new IllegalStateException("Only a PENDING application can be reviewed.");
        }
        this.applicationStatus = JobApplicationStatus.REVIEWED;
    }

    public void accept() {
        if (this.applicationStatus != JobApplicationStatus.REVIEWED) {
            throw new IllegalStateException("Only a REVIEWED application can be accepted.");
        }
        this.applicationStatus = JobApplicationStatus.ACCEPTED;
    }

    public void reject() {
        if (this.applicationStatus != JobApplicationStatus.REVIEWED) {
            throw new IllegalStateException("Only a REVIEWED application can be rejected.");
        }
        this.applicationStatus = JobApplicationStatus.REJECTED;
    }

    public void withdraw() {
        if (this.applicationStatus == JobApplicationStatus.ACCEPTED
                || this.applicationStatus == JobApplicationStatus.REJECTED
                || this.applicationStatus == JobApplicationStatus.WITHDRAWN) {
            throw new IllegalStateException("This application can no longer be withdrawn.");
        }
        this.applicationStatus = JobApplicationStatus.WITHDRAWN;
    }

    public void updateMotivationLetter(String motivationLetter) {
        if (this.applicationStatus != JobApplicationStatus.PENDING) {
            throw new IllegalStateException("Motivation letter can only be updated while pending.");
        }
        if (motivationLetter == null || motivationLetter.trim().length() < 50) {
            throw new IllegalArgumentException("Motivation letter must be at least 50 characters.");
        }
        this.motivationLetter = motivationLetter.trim();
    }

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.applicationStatus == null) {
            this.applicationStatus = JobApplicationStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public JobApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
