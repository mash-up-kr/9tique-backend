package kr.co.mash_up.nine_tique.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSettings {

    @Value(value = "${jwt.header}")
    private String tokenHeader;

    @Value(value = "${jwt.token_expiration_time}")
    private Long tokenExpirationTime;

    @Value(value = "${jwt.token_issuer}")
    private String tokenIssuer;

    @Value(value = "${jwt.token_signing_key}")
    private String tokenSigningKey;

    @Value(value = "${jwt.refresh_token_expiration_time}")
    private Long refreshTokenExpTime;

    public String getTokenHeader() {
        return tokenHeader;
    }

    public Long getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public Long getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }
}
