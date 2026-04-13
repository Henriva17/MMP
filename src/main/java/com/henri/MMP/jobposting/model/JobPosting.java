package com.henri.MMP.jobposting.model;

import com.henri.MMP.Enums.EducationLevel;
import com.henri.MMP.Enums.JobPostingStatus;
import com.henri.MMP.Enums.JobType;
import com.henri.MMP.Enums.WorkMode;
import com.henri.MMP.company.model.Company;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "job_title", nullable = false, length = 255)
    private String jobTitle;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkMode workMode;

    @Column(length = 255)
    private String location;

    @ElementCollection
    @CollectionTable(name = "job_required_skills", joinColumns = @JoinColumn(name = "job_posting_id"))
    @Column(name = "skill", nullable = false)
    private Set<String> requiredSkills = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "required_education_level", nullable = false)
    private EducationLevel requiredEducationLevel;

    @Column(nullable = false)
    private LocalDate applicationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobPostingStatus jobPostingStatus = JobPostingStatus.DRAFT;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected JobPosting() {
    }

    public JobPosting(
            Company company,
            String jobTitle,
            String description,
            JobType jobType,
            WorkMode workMode,
            String location,
            EducationLevel requiredEducationLevel,
            LocalDate applicationDeadline
    ) {
        if (company == null) {
            throw new IllegalArgumentException("Company is required");
        }
        if (jobTitle == null || jobTitle.isBlank()) {
            throw new IllegalArgumentException("Job title is required");
        }
        if (jobType == null) {
            throw new IllegalArgumentException("Job type is required");
        }
        if (workMode == null) {
            throw new IllegalArgumentException("Work mode is required");
        }
        if (applicationDeadline == null || !applicationDeadline.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline must be in the future");
        }

        this.company = company;
        this.jobTitle = jobTitle.trim();
        this.description = description;
        this.jobType = jobType;
        this.workMode = workMode;
        this.location = location;
        this.requiredEducationLevel = requiredEducationLevel;
        this.applicationDeadline = applicationDeadline;
        this.jobPostingStatus = JobPostingStatus.DRAFT;
    }

    public void publish() {
        if (this.jobPostingStatus != JobPostingStatus.DRAFT) {
            throw new IllegalStateException("Only a DRAFT posting can be published.");
        }
        if (this.workMode != WorkMode.REMOTE && (this.location == null || this.location.isBlank())) {
            throw new IllegalStateException("Location is required for onsite or hybrid jobs.");
        }
        if (this.applicationDeadline == null || !this.applicationDeadline.isAfter(LocalDate.now())) {
            throw new IllegalStateException("Application deadline must be in the future.");
        }
        this.jobPostingStatus = JobPostingStatus.OPEN;
    }

    public void close() {
        if (this.jobPostingStatus != JobPostingStatus.OPEN) {
            throw new IllegalStateException("Only an OPEN posting can be closed.");
        }
        this.jobPostingStatus = JobPostingStatus.CLOSED;
    }

    public void archive() {
        if (this.jobPostingStatus == JobPostingStatus.ARCHIVED) {
            throw new IllegalStateException("Job posting is already archived.");
        }
        this.jobPostingStatus = JobPostingStatus.ARCHIVED;
    }

    public void updateRequiredSkills(Set<String> skills) {
        this.requiredSkills.clear();
        if (skills != null) {
            this.requiredSkills.addAll(skills);
        }
    }

    public boolean isOpen() {
        return this.jobPostingStatus == JobPostingStatus.OPEN
                && !LocalDate.now().isAfter(this.applicationDeadline);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.jobPostingStatus == null) {
            this.jobPostingStatus = JobPostingStatus.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public JobType getJobType() {
        return jobType;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public String getLocation() {
        return location;
    }

    public Set<String> getRequiredSkills() {
        return requiredSkills;
    }

    public EducationLevel getRequiredEducationLevel() {
        return requiredEducationLevel;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public JobPostingStatus getJobPostingStatus() {
        return jobPostingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}