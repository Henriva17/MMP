package com.henri.MMP.student.repository;

import com.henri.MMP.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentMat(String studentMat);
    boolean existsByUser_Id(Long userId);
    List<Student> findBySkillsIn(Set<String> skills);
    Optional<Student> findByUser_id(Long user_id);
}