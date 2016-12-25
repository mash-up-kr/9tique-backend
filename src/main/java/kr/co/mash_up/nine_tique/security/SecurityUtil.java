package kr.co.mash_up.nine_tique.security;

import kr.co.mash_up.nine_tique.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {

    private SecurityUtil(){
    }

    public static User getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext.getAuthentication() == null){
            throw new AccessDeniedException("User not found in security session");
        }

        return (User) securityContext.getAuthentication().getPrincipal();
    }
}
