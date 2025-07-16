package org.aldouscloud.aldouscloud.security.signaturev4;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aldouscloud.aldouscloud.entity.AccessKey;
import org.aldouscloud.aldouscloud.service.AccessKeyService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

//@Slf4j
//@RequiredArgsConstructor
//public class SignatureAuthFilter extends OncePerRequestFilter {
//    private final AccessKeyService accessKeyService;
//    private final SignatureValidator signatureValidator;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException{
//        String authHeader = request.getHeader("Authorization");
//        String dateHeader = request.getHeader("Date");
//
//        if(authHeader == null || !authHeader.startsWith("ALDOUS")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        try{
//            String[] parts = authHeader.substring(7).split(":");
//            String accessKeyId = parts[0];
//            String signature = parts[1];
//
//            Optional<AccessKey> optionalKey = accessKeyService.findAccessKeyById(accessKeyId);
//            if(optionalKey.isEmpty()){
//                log.warn("AccessKey not found");
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            AccessKey accessKey = optionalKey.get();
//            if(!accessKey.isActive()){
//                log.warn("AccessKey is not active");
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            boolean valid = signatureValidator.isValidSignature(request,accessKey,signature);
//            if(!valid){
//                log.warn("Invalid signature for key: {}",accessKeyId);
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(accessKey.getUser(),null, accessKey.getUser().getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            accessKeyService.updateLastUsedAt(accessKey.getId());
//        } catch (Exception e){
//            log.error("Failed to validate signature",e);
//        }
//        filterChain.doFilter(request, response);
//    }
//}

@Slf4j
@RequiredArgsConstructor
public class SignatureAuthFilter extends OncePerRequestFilter {

    private final AccessKeyService accessKeyService;
    private final SignatureValidator signatureValidator;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("ALDOUS")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String[] parts = authHeader.substring(7).split(":");
            if (parts.length != 2) {
                log.warn("Invalid Authorization header format");
                filterChain.doFilter(request, response);
                return;
            }

            String accessKeyId = parts[0];
            String signature = parts[1];

            Optional<AccessKey> optionalKey = accessKeyService.findAccessKeyById(accessKeyId);
            if (optionalKey.isEmpty()) {
                log.warn("AccessKey not found");
                filterChain.doFilter(request, response);
                return;
            }

            AccessKey accessKey = optionalKey.get();
            if (!accessKey.isActive()) {
                log.warn("AccessKey is not active");
                filterChain.doFilter(request, response);
                return;
            }

            boolean valid = signatureValidator.isValidSignature(request, accessKey, signature);
            if (!valid) {
                log.warn("Invalid signature for key: {}", accessKeyId);
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            accessKey.getUser(),
                            null,
                            accessKey.getUser().getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            accessKeyService.updateLastUsedAt(accessKey.getId());

        } catch (Exception e) {
            log.error("Failed to validate signature", e);
        }

        filterChain.doFilter(request, response);
    }
}



