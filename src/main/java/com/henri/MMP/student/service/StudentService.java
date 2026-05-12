package com.henri.MMP.student.service;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.student.dto.CreateStudentProfileRequest;
import com.henri.MMP.student.dto.StudentResponse;
import com.henri.MMP.student.mapper.StudentMapper;
import com.henri.MMP.student.model.Student;
import com.henri.MMP.student.repository.StudentRepository;
import com.henri.MMP.user.repository.UserRepository;
import com.henri.MMP.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public StudentResponse createProfile(Long userId, CreateStudentProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (studentRepository.existsByUser_Id(userId)) {
            throw new IllegalStateException("Student profile already exists for this user.");
        }

        if (user.getRole() != Role.USER) {
            throw new IllegalStateException("User already has role: " + user.getRole());
        }

        user.promoteToStudent();

        Student student = new Student(
                user,
                request.studentMat(),
                request.bio(),
                request.fieldOfStudy(),
                request.educationLevel(),
                request.graduationYear(),
                request.university(),
                request.skills()
        );

        student.updateGithubLink(request.githubLink());
        student.updatePortfolioLink(request.portfolioLink());
        student.updateCvFilePath(request.cvFilePath());

        userRepository.save(user);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        return StudentMapper.toResponse(findStudentEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getBySkill(Set<String> skills) {
        return studentRepository.findBySkillsIn(skills)
                .stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    public StudentResponse updateBio(Long id, String bio) {
        Student student = findStudentEntityById(id);
        student.updateBio(bio);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponse updateSkills(Long id, Set<String> skills) {
        Student student = findStudentEntityById(id);
        student.updateSkills(skills);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    public void deleteProfile(Long id) {
        Student student = findStudentEntityById(id);
        User user = student.getUser();

        studentRepository.delete(student);
        user.resetRoleToUser();
        userRepository.save(user);
    }

    protected Student findStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }
}