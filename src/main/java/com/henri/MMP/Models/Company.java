package com.henri.MMP.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "company_profiles")
public class Company {

    @Id
    @Column(name = "user_id")
    private Long companyId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Domain of activity is required")
    private String domainOfActivity;

    @Column
    private String websiteLink;

    @Column(nullable = false)
    @Positive(message = "Company size must be greater than 0")
    private Integer companySize;

    @Column(nullable = false)
    private boolean verified;

    protected Company() {}

    public Company(User user, String description, String domainOfActivity, String websiteLink, Integer companySize) {

        this.user = user;
        this.description = description;
        this.domainOfActivity = domainOfActivity;
        this.websiteLink = websiteLink;
        this.companySize = companySize;
        this.verified = false;
    }

    public void verify() {this.verified = true;}

    public void updateDescription(String description) {this.description = description;}

    public void updateWebsiteLink(String websiteLink) {this.websiteLink = websiteLink;}

    public Long getCompanyId() {return companyId;}

    public User getUser() {return user;}

    public String getDescription() {return description;}

    public String getDomainOfActivity() {return domainOfActivity;}

    public String getWebsiteLink() {return websiteLink;}

    public Integer getCompanySize() {return companySize;}

    public boolean isVerified() {return verified;}
}
