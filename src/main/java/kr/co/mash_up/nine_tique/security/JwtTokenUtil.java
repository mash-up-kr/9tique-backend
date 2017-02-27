package kr.co.mash_up.nine_tique.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import kr.co.mash_up.nine_tique.config.JwtSettings;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_SUB = "";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    static final String CLAIM_KEY_USER_ID = "user_id";
    static final String CLAIM_KEY_OAUTH_TOKEN = "oauth_token";
    static final String CLAIM_KEY_OAUTH_TYPE = "oauth_type";
    static final String CLAIM_KEY_ROLES = "roles";

    @Autowired
    private JwtSettings jwtSettings;

    public Boolean validateToken(String token) throws ServletException {
        return !isTokenExpired(token);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtSettings.getTokenExpirationTime() * 1000);
    }

    private Boolean isTokenExpired(String token) throws ServletException {
        final Date expiration = getExpirationAtFromToken(token);
        return expiration.before(new Date());
    }

    public String refreshToken(String token) {
        String refreshedToken;
        final Claims claims = getClaimsFromToken(token);
        refreshedToken = generateToken(claims);

        return refreshedToken;
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject("access_token")
                .setIssuer(jwtSettings.getTokenIssuer())  // token 발급자
                .setIssuedAt(new Date())  // 발급시간
                .setExpiration(generateExpirationDate())  // 만료시간
                .signWith(SignatureAlgorithm.HS256, jwtSettings.getTokenSigningKey())
                .compact();
    }

    public String generateToken(User user) {
        String authority = user.findAuthority();

        Map<String, Object> claims = new HashMap<>();
        // public 클레임
        claims.put(jwtSettings.getTokenIssuer() + "/jwt_claims", true);
        // private 클레임
        claims.put(CLAIM_KEY_USER_ID, user.getId().toString());
        claims.put(CLAIM_KEY_OAUTH_TOKEN, user.getOauthToken());
        claims.put(CLAIM_KEY_OAUTH_TYPE, user.getOauthType());
        claims.put(CLAIM_KEY_ROLES, authority);

        return generateToken(claims);
    }

    public Claims getClaimsFromToken(String token) {
        try {

            return Jwts.parser()
                    .setSigningKey(jwtSettings.getTokenSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (final SignatureException e) {
            throw new InvalidTokenException();
        }
    }

    public String getSubjectFromToken(String token) throws ServletException {

        Claims claims = getClaimsFromToken(token);

        return claims.getSubject();
    }

    public Date getIssuedAtFromToken(String token) throws ServletException {
        Claims claims = getClaimsFromToken(token);

        return claims.getIssuedAt();
    }

    public String getIssuerFromToken(String token) throws ServletException {
        Claims claims = getClaimsFromToken(token);

        return claims.getIssuer();
    }

    public Date getExpirationAtFromToken(String token) throws ServletException {
        Claims claims = getClaimsFromToken(token);

        return claims.getExpiration();
    }

    /********** private claim **************/
    public Long getUserIdFromToken(String token) throws ServletException {
        Long userId;

        Claims claims = getClaimsFromToken(token);

        userId = Long.valueOf((String) claims.get("user_id"));
        return userId;
    }

    public String getOauthTokenFromToken(String token) throws ServletException {
        String oauthToken;

        Claims claims = getClaimsFromToken(token);

        oauthToken = (String) claims.get("oauth_token");
        return oauthToken;
    }

    public String getOauthTypeFromToken(String token) throws ServletException {
        String oauthType;

        Claims claims = getClaimsFromToken(token);
        oauthType = (String) claims.get("oauth_type");

        return oauthType;
    }

    public String getRolesFromToken(String token) throws ServletException {
        String roles;

        Claims claims = getClaimsFromToken(token);

        roles = (String) claims.get("roles");
        return roles;
    }
}
