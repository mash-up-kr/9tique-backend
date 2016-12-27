package kr.co.mash_up.nine_tique.security;

import kr.co.mash_up.nine_tique.config.JwtSettings;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtSettings jwtSettings;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /*
        header
        Authorization: Bearer <token>
         */
        final String authHeader = request.getHeader(jwtSettings.getTokenHeader());

        if (authHeader != null) {
            if (!authHeader.startsWith(HEADER_PREFIX)) {
                throw new ServletException("Missing or invalid Authorization header.");
            }

            logger.info(authHeader.toString());

            final String token = authHeader.substring(HEADER_PREFIX.length());  // The part after "Bearer "

            if (!jwtTokenUtil.validateToken(token)) {
                throw new ServletException("Invaild token.");
            }

            Long userId = jwtTokenUtil.getUserIdFromToken(token);
            log.info("user id " + userId);
            User user = userRepository.findOne(userId);

            if (user == null) {
                //Todo: 에러 처리 핸들러 구현
                throw new IdNotFoundException("user not found");
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthoritiesWithoutPersistence());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user " + userId + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
