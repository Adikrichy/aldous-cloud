package org.aldouscloud.aldouscloud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.aldouscloud.aldouscloud.enums.user.Role;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String fullName;
    @NotBlank
    private String password;
    private Role role;
    // Роль автомат будет root потом только root может создавать IAM
}
