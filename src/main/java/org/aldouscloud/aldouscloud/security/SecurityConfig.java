package org.aldouscloud.aldouscloud.security;

import lombok.RequiredArgsConstructor;
import org.aldouscloud.aldouscloud.security.signaturev4.SignatureAuthFilter;
import org.aldouscloud.aldouscloud.security.signaturev4.SignatureValidator;
import org.aldouscloud.aldouscloud.service.AccessKeyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.aldouscloud.aldouscloud.security.JwtAuthFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final JwtAuthFilter jwtAuthFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http,
//                                                   SignatureAuthFilter signatureAuthFilter) throws Exception {
//        http.
//                csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/access-keys/**").hasRole("ROOT")
//                        .requestMatchers(HttpMethod.POST,"/api/auth/register").permitAll()
//                        .requestMatchers("/swagger-ui/**",
//                                         "/v3/api-docs/**",
//                                         "/swagger-resources/**").permitAll()
//                        .anyRequest().authenticated())
//                .addFilterBefore(signatureAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .httpBasic(httpBasic -> httpBasic.disable());
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                               SignatureAuthFilter signatureAuthFilter) throws Exception {
    http.
            csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/access-keys/**").hasRole("ROOT")
                    .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                    .requestMatchers("/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/api/auth/login").permitAll()
                    .anyRequest().authenticated())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(signatureAuthFilter, JwtAuthFilter.class)
            .httpBasic(httpBasic -> httpBasic.disable());
    return http.build();
}

    @Bean
    public SignatureAuthFilter signatureAuthFilter(AccessKeyService accessKeyService,
                                                   SignatureValidator signatureValidator) {
        return new SignatureAuthFilter(accessKeyService, signatureValidator);
    }
}
