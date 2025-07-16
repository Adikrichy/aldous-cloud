package org.aldouscloud.aldouscloud.service;

import jakarta.servlet.http.HttpServletResponse;
import org.aldouscloud.aldouscloud.dto.request.LoginRequest;
import org.aldouscloud.aldouscloud.dto.request.RegisterRequest;
import org.aldouscloud.aldouscloud.dto.response.LoginResponse;
import org.aldouscloud.aldouscloud.dto.response.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest, HttpServletResponse response);
    void logout(HttpServletResponse response);
    RegisterResponse register(RegisterRequest registerRequest);
}
