package com.henri.MMP.login.service;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.company.model.Company;
import com.henri.MMP.company.repository.CompanyRepository;
import com.henri.MMP.login.dto.AuthResponse;
import com.henri.MMP.login.dto.LoginRequest;
import com.henri.MMP.student.model.Student;
import com.henri.MMP.student.repository.StudentRepository;
import com.henri.MMP.user.model.User;
import com.henri.MMP.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public AuthService(UserRepository userRepository,
                       JwtService jwtService, CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    public AuthResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);


        // fetch studentId and companyId
        Long studentId = null;
        Long companyId = null;

        if (user.getRole() == Role.STUDENT) {
            studentId = studentRepository.findByUser_id(user.getId())
                    .map(Student::getId)
                    .orElse(null);
        } else if (user.getRole() == Role.COMPANY) {
            companyId = companyRepository.findById(user.getId())
                    .map(Company::getId)
                    .orElse(null);
        }

        System.out.println("=== AUTH RESPONSE DEBUG ===");
        System.out.println("role: " + user.getRole());
        System.out.println("userId: " + user.getId());
        System.out.println("studentId: " + studentId);
        System.out.println("companyId: " + companyId);


        return new AuthResponse(token, user.getRole(), user.getId(), studentId, companyId, user.getFullName());
    }
}
