package com.henri.MMP.user.service;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.Status;
import com.henri.MMP.user.dto.RegisterUserRequest;
import com.henri.MMP.user.dto.UserResponse;
import com.henri.MMP.user.mapper.UserMapper;
import com.henri.MMP.user.model.User;
import com.henri.MMP.user.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public UserResponse register(RegisterUserRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        User user = new User(
                request.fullName(),
                email,

                request.password()

                passwordEncoder.encode(request.password())

        );

        return UserMapper.toResponse(userRepository.save(user));
    }
    
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return UserMapper.toResponse(findUserEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse updateStatus(Long id, Status status) {
        User user = findUserEntityById(id);

        switch (status) {
            case ACTIVE -> user.activate();
            case INACTIVE -> user.deactivate();
            case BLOCKED -> user.block();
        }

        return UserMapper.toResponse(userRepository.save(user));
    }

    public void deactivateUser(Long id) {
        User user = findUserEntityById(id);
        user.deactivate();
        userRepository.save(user);
    }

    protected User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
}
