package com.henri.MMP.student.mapper;

import com.henri.MMP.student.dto.StudentResponse;
import com.henri.MMP.student.model.Student;

public final class StudentMapper {

    private StudentMapper() {
    }

    public static StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getUser().getId(),
                student.getUser().getFullName(),
                student.getUser().getEmail(),
                student.getStudentMat(),
                student.getBio(),
                student.getFieldOfStudy(),
                student.getEducationLevel(),
                student.getGraduationYear(),
                student.getUniversity(),
                student.getSkills(),
                student.getGithubLink(),
                student.getPortfolioLink(),
                student.getCvFilePath()
        );
    }
}