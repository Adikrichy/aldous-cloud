package org.aldouscloud.aldouscloud.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.request.LoginRequest;
import org.aldouscloud.aldouscloud.dto.request.RegisterRequest;
import org.aldouscloud.aldouscloud.dto.response.LoginResponse;
import org.aldouscloud.aldouscloud.dto.response.RegisterResponse;
import org.aldouscloud.aldouscloud.repository.UserRepository;
import org.aldouscloud.aldouscloud.security.JWTService;
import org.aldouscloud.aldouscloud.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest
                                              ,HttpServletResponse httpServletResponse){
        LoginResponse loginResponse = authService.login(loginRequest, httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse httpServletResponse){
        authService.logout(httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
         RegisterResponse response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
