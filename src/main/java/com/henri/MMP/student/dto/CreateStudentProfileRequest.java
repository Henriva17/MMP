package com.henri.MMP.student.dto;

import com.henri.MMP.Enums.EducationLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateStudentProfileRequest(
        @NotBlank(message = "Student matricule is required")
        String studentMat,

        String bio,

        @NotBlank(message = "Field of study is required")
        String fieldOfStudy,

        @NotNull(message = "Education level is required")
        EducationLevel educationLevel,

        @NotNull(message = "Graduation year is required")
        Integer graduationYear,

        @NotBlank(message = "University is required")
        String university,

        Set<String> skills,

        String githubLink,
        String portfolioLink,
        String cvFilePath
) {
}