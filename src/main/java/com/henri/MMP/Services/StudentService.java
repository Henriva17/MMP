package com.henri.MMP.Services;
import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.WorkMode;
import com.henri.MMP.Models.Student;
import com.henri.MMP.Models.User;
import com.henri.MMP.Repositories.StudentRepository;
import com.henri.MMP.Repositories.UserRepository;
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

    public Student createProfile(Long userId, Student request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (studentRepository.existsByUsertId(userId)) {
            throw new IllegalStateException("Student profile already exists for this user.");
        }

        if (user.getRole() != Role.USER) {
            throw new IllegalStateException("User already has role: " + user.getRole());
        }

        user.promoteToStudent();

        Student student = new Student(
                userId,  // which is gelijk aan StudentId defineert in de Model Constructor claas dat is gewoon for duidelijkheid
                request.getStudentMat(),
                request.getBio(),
                request.getFieldOfStudy(),
                request.getEducationLevel(),
                request.getGraduationYear(),
                request.getUniversity(),
                request.getSkills()

        );

        userRepository.save(user);
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    @Transactional(readOnly = true)
    public Student getStudentId(Student studentId) {
        return studentRepository.findByStudentId(studentId.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found."));
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Student> getByWorkMode(WorkMode workMode) {
        return studentRepository.findByWorkMode(workMode);
    }

    @Transactional(readOnly = true)
    public List<Student> getBySkill(Set<String> skill) {
        return studentRepository.findBySkillsIn(skill);
    }

    public Student updateBio(Long userId, String bio) {
        Student student = getById(userId);
        student.updateBio(bio);
        return studentRepository.save(student);
    }

    public Student updateSkills(Long userId, Set<String> skills) {
        Student student = getById(userId);
        student.updateSkills(skills);
        return studentRepository.save(student);
    }
}
