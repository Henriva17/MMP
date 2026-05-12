package com.henri.MMP.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(length = 500)
    private String profilePicture;

    @Column(length = 500)
    private String linkedinLink;

    @Column(length = 255)
    private String location;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email.trim().toLowerCase();
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) {
            this.role = Role.USER;
        }
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
    }

    public void promoteToStudent() {

        ensureBaseUser();

        ensureBaseUser();           // Er kijk of een user een andere role hebt anders dan user als er juiste is dan toen een fout

        this.role = Role.STUDENT;
    }

    public void promoteToCompany() {

        ensureBaseUser();

        ensureBaseUser();            // Er kijk of een user een andere role hebt anders dan user als er juiste is dan toen een fout

        this.role = Role.COMPANY;
    }

    public void resetRoleToUser() {
        this.role = Role.USER;
    }

    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void block() {
        this.status = Status.BLOCKED;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void updateProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void updateLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public void updateLocation(String location) {
        this.location = location;
    }


    // Er kijk of een user een andere role hebt anders dan user als er juiste is dan toen een fout

    private void ensureBaseUser() {
        if (this.role != Role.USER) {
            throw new IllegalStateException("User already has role: " + this.role);
        }
    }

    public Long getId() {
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

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}