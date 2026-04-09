package com.henri.MMP.Services;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.Status;
import com.henri.MMP.Models.User;
import com.henri.MMP.Repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        User user = new User(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public User updateStatus(Long id, Status status) {
        User user = getById(id);
        user.setStatus(status);
        return userRepository.save(user);
    }

    public User deactivateUser(Long id) {
        User user = getById(id);
        user.setStatus(Status.INACTIVE);
        return userRepository.save(user);
    }
}
