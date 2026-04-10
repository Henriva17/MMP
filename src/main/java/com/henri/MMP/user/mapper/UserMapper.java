package com.henri.MMP.user.mapper;

import com.henri.MMP.user.dto.UserResponse;
import com.henri.MMP.user.model.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getProfilePicture(),
                user.getLinkedinLink(),
                user.getLocation(),
                user.getCreatedAt()
        );
    }
}
