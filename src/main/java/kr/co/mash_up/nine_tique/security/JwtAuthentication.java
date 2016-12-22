package kr.co.mash_up.nine_tique.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * 사용 안함
 */
public class JwtAuthentication implements Authentication {

    private List<SimpleGrantedAuthority> authorities;
    private String name;  // user id;
    private String principal;  // user email
    private String credentials;  // oauth id
    private String details;  // oauth type;
    private boolean authenticated;

    public JwtAuthentication(List<SimpleGrantedAuthority> authorities, String name, String principal, String credentials, String details) {
        this.authorities = authorities;
        this.name = name;
        this.principal = principal;
        this.credentials = credentials;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }


    @Override
    public String getName() {
        return null;
    }
}
