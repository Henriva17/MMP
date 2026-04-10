package com.henri.MMP.company.model;


import com.henri.MMP.Enums.VerificationStatus;
import com.henri.MMP.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "company_profiles")
public class Company {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Domain of activity is required")
    @Column(name = "domain_of_activity", nullable = false, length = 255)
    private String domainOfActivity;

    @Column(name = "website_link", length = 500)
    private String websiteLink;

    @Positive(message = "Company size must be greater than 0")
    @Column(name = "company_size", nullable = false)
    private Integer companySize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    protected Company() {
    }

    public Company(User user, String description, String domainOfActivity, String websiteLink, Integer companySize) {
        this.user = user;
        this.description = description;
        this.domainOfActivity = domainOfActivity;
        this.websiteLink = websiteLink;
        this.companySize = companySize;
        this.verificationStatus = VerificationStatus.PENDING;
    }

    public void verify() {
        this.verificationStatus = VerificationStatus.VERIFIED;
    }

    public void rejectVerification() {
        this.verificationStatus = VerificationStatus.REJECTED;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public void updateCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getDomainOfActivity() {
        return domainOfActivity;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }
}

