package kr.co.mash_up.nine_tique.security;

import kr.co.mash_up.nine_tique.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {

    private SecurityUtil(){
    }

    public static User getCurrentUser(){
        // Todo: 2017.07.11 test용으로 주석 구현 완료 후 해제할 것
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        if(securityContext.getAuthentication() == null){
//            throw new AccessDeniedException("User not found in security session");
//        }
//
//        return (User) securityContext.getAuthentication().getPrincipal();

        User user = new User();
        user.setId(1L);
        return user;
    }
}
