package org.aldouscloud.aldouscloud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String email;
    private String role;
    private String message;
    private long expiresAt;
}
