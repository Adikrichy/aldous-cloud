package org.aldouscloud.aldouscloud.mapper;

import org.aldouscloud.aldouscloud.dto.response.RegisterResponse;
import org.aldouscloud.aldouscloud.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper {
    public static RegisterResponse toDto(User user){
        if(user ==null) return null;
        return new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                "User registered successfuly",
                user.getRole()
        );
    }
}
