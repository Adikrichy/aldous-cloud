package org.aldouscloud.aldouscloud.mapper;

import org.aldouscloud.aldouscloud.dto.response.LoginResponse;
import org.aldouscloud.aldouscloud.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public static LoginResponse toDto(User user){
        if(user ==null) return null;
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                "Login successful",
                System.currentTimeMillis() + (3600 * 1000)
        );
    }
}
