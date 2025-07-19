package org.aldouscloud.aldouscloud.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    public static String getCurrentUsername(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new org.springframework.security.access.AccessDeniedException("Unauthenticated access");
        }

        Object principal = auth.getPrincipal();
        if(principal instanceof UserDetails userDetails){
            return userDetails.getUsername();
        }else if(principal instanceof String){
            return (String) principal;
        }
        throw new RuntimeException("Unknown principal type: " + principal);
    }
}
