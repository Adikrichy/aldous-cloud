package org.aldouscloud.aldouscloud.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.entity.User;
import org.aldouscloud.aldouscloud.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    String email = jwtService.extractUsername(token); // subject из токена

                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                        // Проверка токена + соответствие email
                        if (jwtService.isTokenValid(token, user)) {
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
