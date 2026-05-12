package com.henri.MMP.user.dto;

import com.henri.MMP.Enums.Role;
import com.henri.MMP.Enums.Status;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        Status status,
        String profilePicture,
        String linkedinLink,
        String location,
        LocalDateTime createdAt
) {
}