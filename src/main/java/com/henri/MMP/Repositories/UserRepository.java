package com.henri.MMP.Repositories;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.Status;
import com.henri.MMP.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByRoleAndStatus(Role role, Status status);
}
