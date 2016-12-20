package kr.co.mash_up.nine_tique.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Autowired
    UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader == null){
            throw new ServletException("Missing or invalid Authorization header.");
        }
        logger.info(authHeader.toString());

        try {
            String secretKey = "qTLY/BYBom546U8mvYwdE/59JbYY+qKucaEme8Z8jQbyF5MvXuWNnkJOmTSguaWbB9R00hpoI/DUdZF2zee26A";
            final Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(authHeader)
                    .getBody();
            logger.info(claims);
            httpRequest.setAttribute("claims", claims);
        } catch (final SignatureException e) {
            throw new ServletException("Invaild token.");
        }

        chain.doFilter(request, response);
    }
}
