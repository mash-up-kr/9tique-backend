package kr.co.mash_up.nine_tique.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import kr.co.mash_up.nine_tique.config.JwtSettings;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /*
        header
        Authorization: Bearer <token>
         */
        final String authHeader = request.getHeader(jwtSettings.getTokenHeader());

        if (authHeader != null) {
            if (!authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header.");
            }

            logger.info(authHeader.toString());

            final String token = authHeader.substring(7);  // The part after "Bearer "

            try {
                final Claims claims = Jwts.parser()
                        .setSigningKey(jwtSettings.getTokenSigningKey())
                        .parseClaimsJws(token)
                        .getBody();
                logger.info(claims);

                //Todo: expire time check
//            LocalDateTime expriedTime = LocalDateTime.parse((CharSequence) claims.get("expried"));
//            if(expriedTime.plusHours(30).isBefore(LocalDateTime.now())){
                //Todo: throw expired exception
//            }

                Long userId = Long.valueOf((String) claims.get("user_id"));
                log.info("user id " + userId);
                User user = userRepository.findOne(userId);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (user != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthoritiesWithoutPersistence());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        log.info("authenticated user " + userId + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            } catch (final SignatureException e) {
                throw new ServletException("Invaild token.");
            }
        }

        filterChain.doFilter(request, response);
    }
}
