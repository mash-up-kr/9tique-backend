package kr.co.mash_up.nine_tique.security;

import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        Long userId = (Long) authentication.getPrincipal();
        log.info("authenticate user id" + userId);

        User user = userRepository.findOne(userId);

//        Optional.of(user).orElseThrow(() -> new RuntimeException("user not found"));

        if (user == null) {
            //Todo: throw user not found exception
            throw new RuntimeException("user not found");
        }

        return new UsernamePasswordAuthenticationToken(user.getId(), null, user.getAuthoritiesWithoutPersistence());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;

    }
}