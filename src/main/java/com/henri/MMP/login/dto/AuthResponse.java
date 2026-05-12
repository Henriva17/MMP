package com.henri.MMP.login.dto;

import com.henri.MMP.Enums.Role;

public record AuthResponse(
        String token,
        Role role,
        Long userId,
        Long studentId,
        Long companyId,
        String fullName


) {}

