package com.henri.MMP.student.dto;

import com.henri.MMP.Enums.EducationLevel;

import java.util.Set;

public record StudentResponse(
        Long id,
<<<<<<< HEAD
        Long userId,
=======
        Long userId, //
>>>>>>> henridev
        String fullName,
        String email,
        String studentMat,
        String bio,
        String fieldOfStudy,
        EducationLevel educationLevel,
        Integer graduationYear,
        String university,
        Set<String> skills,
        String githubLink,
        String portfolioLink,
        String cvFilePath
) {
}
