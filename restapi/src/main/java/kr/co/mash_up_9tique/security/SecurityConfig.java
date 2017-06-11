package kr.co.mash_up_9tique.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static kr.co.mash_up_9tique.util.Constant.RestEndpoint.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * create 401 error handler Bean
     *
     * @return 401 error handler
     */
    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPointBean() {
        return new RestAuthenticationEntryPoint();
    }

    /**
     * create 403 error handler Bean
     *
     * @return 403 error handler
     */
    @Bean
    public AccessDeniedHandler noRedirectingAccessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider();
        return provider;
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .authenticationProvider(authenticationProvider());

        auth.inMemoryAuthentication()
                .withUser("user").password("pass")
                .roles(Authorities.USER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointBean())  // 401 error handle

                .and()
                .exceptionHandling()
                .accessDeniedHandler(noRedirectingAccessDeniedHandler())  // 403 error handle

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
//                .antMatchers(STORAGE + SUFFIX).permitAll()
//                .antMatchers(H2_CONSOLE + SUFFIX).permitAll()

                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, API_USER + "/login" + SUFFIX).permitAll()
                .antMatchers(HttpMethod.PUT, API_USER + "/register" + SUFFIX).hasAnyAuthority(Authorities.USER)

                //Todo: 카테고리 권한 주석 제거
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.USER)
//                .antMatchers(HttpMethod.POST, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.ADMIN)
//                .antMatchers(HttpMethod.PUT, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.ADMIN)
//                .antMatchers(HttpMethod.DELETE, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.ADMIN)
                .antMatchers(HttpMethod.POST, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.PUT, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.DELETE, API_CATEGORY + SUFFIX).hasAnyAuthority(Authorities.USER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, API_PRODUCT + SUFFIX).hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.POST, API_PRODUCT + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.PUT, API_PRODUCT + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.DELETE, API_PRODUCT + SUFFIX).hasAnyAuthority(Authorities.SELLER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, API_PRODUCT_IMAGE + SUFFIX).hasAnyAuthority(Authorities.SELLER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, API_ZZIM + SUFFIX).hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.POST, API_ZZIM + SUFFIX).hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.DELETE, API_ZZIM + SUFFIX).hasAnyAuthority(Authorities.USER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, API_SHOP + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.GET, API_SHOP + SUFFIX).hasAnyAuthority(Authorities.ADMIN)
                .antMatchers(HttpMethod.POST, API_SHOP + SUFFIX).hasAnyAuthority(Authorities.ADMIN)
                .antMatchers(HttpMethod.PUT, API_SHOP + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.PUT, API_SHOP + SUFFIX).hasAnyAuthority(Authorities.ADMIN)
                .antMatchers(HttpMethod.DELETE, API_SHOP + SUFFIX).hasAnyAuthority(Authorities.ADMIN)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, API_SELLER + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.POST, API_SELLER + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.PUT, API_SELLER + "/register" + SUFFIX).hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.PUT, API_SELLER + SUFFIX).hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.DELETE, API_SELLER + SUFFIX).hasAnyAuthority(Authorities.SELLER)
//                .antMatchers(HttpMethod.DELETE, API_SELLER + SUFFIX).hasAnyAuthority(Authorities.ADMIN)

                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
    }

    /*
    설정은 덮어쓰기 된다
    http configure에 설정되어 있는게 우선시 된다
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html")
//                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**")
                .antMatchers(HttpMethod.POST, API_USER + "/login" + SUFFIX)
                .antMatchers(STORAGE + SUFFIX)
                .antMatchers(H2_CONSOLE + SUFFIX);
    }
}