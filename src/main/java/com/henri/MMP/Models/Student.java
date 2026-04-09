package com.henri.MMP.Models;

import com.henri.MMP.Enums.EducationLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_profile")
public class Student {

    @Id
    @Column(name = "user_id")
    private Long studentId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @Column(unique = true, nullable = false)
    private String studentMat;

    @Column(length = 1000)
    private String bio;

    @Column(nullable = false)
    @NotBlank(message = "Field of study is required")
    private String fieldOfStudy;

    @Column(nullable = false)
    @NotNull(message = "Education level is required")
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Column(nullable = false)
    @NotNull(message = "Graduation year is required")
    private Integer graduationYear;

    @Column(nullable = false)
    @NotBlank(message = "University  is required")
    private String university;

    @ElementCollection
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    @Column
    private String githubLink;

    @Column
    private String portfolioLink;

    @Column
    private String cvFilePath;

    protected Student() {}

    public Student(Long studentId, String studentMat, String bio, String fieldOfStudy, EducationLevel educationLevel, Integer graduationYear, String university, Set<String> skills) {
        if (user == null) throw new IllegalArgumentException("User is required");
        if (studentMat == null || studentMat.isBlank()) throw new IllegalArgumentException("Student matricule is required");
        if (fieldOfStudy == null || fieldOfStudy.isBlank()) throw new IllegalArgumentException("Field of study is required");
        if (educationLevel == null) throw new IllegalArgumentException("Education level is required");
        if (graduationYear == null) throw new IllegalArgumentException("Graduation year is required");
        if (university == null || university.isBlank()) throw new IllegalArgumentException("University is required");


        this.studentId = studentId;
        this.studentMat = studentMat;
        this.bio = bio;
        this.fieldOfStudy = fieldOfStudy;
        this.educationLevel = educationLevel;
        this.graduationYear = graduationYear;
        this.university = university;
        this.skills = skills;
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

    public Long getStudentId() {return studentId;}

    public User getUser() {return user;}

    public String getStudentMat(){return studentMat;}

    public String getBio() {return bio;}

    public String getFieldOfStudy() {return fieldOfStudy;}

    public EducationLevel getEducationLevel() {return educationLevel;}

    public Integer getGraduationYear() {return graduationYear;}

    public String getUniversity() {return university;}

    public Set<String> getSkills() {return skills;}

    public String getGithubLink() {return githubLink;}

    public String getPortfolioLink() {return portfolioLink;}

    public String getCvFilePath() { return cvFilePath;}

}
