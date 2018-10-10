package kr.co.mash_up.nine_tique.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.User;

/**
 * Created by ethan.kim on 2018. 7. 31..
 */
public class UserAuditorAware implements AuditorAware<Optional<User>> {

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.isAuthenticated() == false
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.of((User) authentication.getPrincipal());
    }
}
