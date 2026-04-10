package com.henri.MMP.student.model;

import com.henri.MMP.Enums.EducationLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.henri.MMP.user.model.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_profiles")
public class Student {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    @Column(name = "student_mat", nullable = false, unique = true, length = 100)
    private String studentMat;

    @Column(length = 1000)
    private String bio;

    @NotBlank(message = "Field of study is required")
    @Column(name = "field_of_study", nullable = false, length = 255)
    private String fieldOfStudy;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Education level is required")
    @Column(name = "education_level", nullable = false)
    private EducationLevel educationLevel;

    @NotNull(message = "Graduation year is required")
    @Column(name = "graduation_year", nullable = false)
    private Integer graduationYear;

    @NotBlank(message = "University is required")
    @Column(nullable = false, length = 255)
    private String university;

    @ElementCollection
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill", nullable = false)
    private Set<String> skills = new HashSet<>();

    @Column(length = 500)
    private String githubLink;

    @Column(length = 500)
    private String portfolioLink;

    @Column(length = 500)
    private String cvFilePath;

    protected Student() {
    }

    public Student(
            User user,
            String studentMat,
            String bio,
            String fieldOfStudy,
            EducationLevel educationLevel,
            Integer graduationYear,
            String university,
            Set<String> skills
    ) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (studentMat == null || studentMat.isBlank()) {
            throw new IllegalArgumentException("Student matricule is required");
        }

        this.user = user;
        this.studentMat = studentMat.trim();
        this.bio = bio;
        this.fieldOfStudy = fieldOfStudy;
        this.educationLevel = educationLevel;
        this.graduationYear = graduationYear;
        this.university = university;
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateSkills(Set<String> skills) {
        this.skills.clear();
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }

    public void updateGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public void updatePortfolioLink(String portfolioLink) {
        this.portfolioLink = portfolioLink;
    }

    public void updateCvFilePath(String cvFilePath) {
        this.cvFilePath = cvFilePath;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getStudentMat() {
        return studentMat;
    }

    public String getBio() {
        return bio;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public String getUniversity() {
        return university;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getPortfolioLink() {
        return portfolioLink;
    }

    public String getCvFilePath() {
        return cvFilePath;
    }
}
