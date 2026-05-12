package com.henri.MMP.student.controller;

import com.henri.MMP.student.dto.CreateStudentProfileRequest;
import com.henri.MMP.student.dto.StudentResponse;
import com.henri.MMP.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
<<<<<<< HEAD

=======
@CrossOrigin(origins = "http://localhost:4200")
>>>>>>> henridev
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<StudentResponse> createProfile(
            @PathVariable Long userId,
            @Valid @RequestBody CreateStudentProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
<<<<<<< HEAD
                .body(studentService.createProfile(userId, request));
=======
                .body(studentService.createNewStudent(userId, request));
>>>>>>> henridev
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping("/skills")
    public ResponseEntity<List<StudentResponse>> getBySkill(@RequestParam Set<String> values) {
        return ResponseEntity.ok(studentService.getBySkill(values));
    }

    @PutMapping("/{id}/bio")
    public ResponseEntity<StudentResponse> updateBio(@PathVariable Long id, @RequestParam String bio) {
        return ResponseEntity.ok(studentService.updateBio(id, bio));
    }

    @PutMapping("/{id}/skills")
    public ResponseEntity<StudentResponse> updateSkills(@PathVariable Long id, @RequestBody Set<String> skills) {
        return ResponseEntity.ok(studentService.updateSkills(id, skills));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        studentService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
