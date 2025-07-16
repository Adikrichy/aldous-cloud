package org.aldouscloud.aldouscloud.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.dto.request.LoginRequest;
import org.aldouscloud.aldouscloud.dto.request.RegisterRequest;
import org.aldouscloud.aldouscloud.dto.response.LoginResponse;
import org.aldouscloud.aldouscloud.dto.response.RegisterResponse;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.enums.user.Role;
import org.aldouscloud.aldouscloud.exceptions.EntityNotFoundException;
import org.aldouscloud.aldouscloud.mapper.AuthMapper;
import org.aldouscloud.aldouscloud.mapper.RegisterMapper;
import org.aldouscloud.aldouscloud.repository.UserRepository;
import org.aldouscloud.aldouscloud.security.JWTService;
import org.aldouscloud.aldouscloud.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new EntityNotFoundException("User not found", loginRequest.getEmail()));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        String token = jwtService.generateToken(user);
        long expiresAt = System.currentTimeMillis() + 3600 * 1000;

        Cookie cookie = new Cookie("JWT",token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); //https
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return AuthMapper.toDto(user);
    }
    @Override
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole() !=null ? registerRequest.getRole(): Role.ROOT);
        userRepository.save(user);

        return RegisterMapper.toDto(user);
    }
}
