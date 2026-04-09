package com.henri.MMP.Repositories;
import com.henri.MMP.Enums.WorkMode;
import com.henri.MMP.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Long studentId);  // of if gwwon maar sind zij zijn gelijk geen pb
    Optional<Student> findByStudentMat(String studentMat);
    List<Student> findBySkillsIn(Set<String> skills);
    List<Student> findByWorkMode(WorkMode workMode);
    boolean existsByUsertId(Long userId);  // een type van validation for dat wij een student profile aanmaken omd- de huidige role te controlleren
}
