package org.aldouscloud.aldouscloud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.aldouscloud.aldouscloud.enums.user.Role;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String email;
    private String fullName;
    private String message;
    private Role role;
}
