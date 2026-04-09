package com.henri.MMP.Models;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message ="FullName is required")
    @Column(nullable = false)
    private String fullName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email os required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private String profilePicture;

    @Column
    private String linkedinLinkPage;

    private String location;

    private LocalDateTime createdAt;

    protected User(){}

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    // Domain methods over setters

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.role == null) {
            this.role = Role.USER;
        }
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
    }

    public void promoteToStudent() {
        if (this.role != Role.USER) {
            throw new IllegalStateException("Only a plain USER can be promoted to STUDENT");
        }
        this.role = Role.STUDENT;
    }

    public void promoteToCompany() {
        if (this.role != Role.USER) {
            throw new IllegalStateException("Only a plain USER can be promoted to COMPANY");
        }
        this.role = Role.COMPANY;
    }

    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void updateProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void updateLinkedinLinkPage(String linkedinLinkPage) {
        this.linkedinLinkPage = linkedinLinkPage;
    }

    public void updateLocation(String location) {
        this.location = location;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Long getById() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getLinkedinLinkPage() {
        return linkedinLinkPage;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}








