package kr.co.mash_up.nine_tique.security;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * create 401 error handler Bean
     * @return 401 error handler
     */
    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPointBean() {
        return new RestAuthenticationEntryPoint();
    }

    /**
     * create 403 error handler Bean
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
                .antMatchers("/login").permitAll()
                .antMatchers("/storage/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/user/**").hasAnyAuthority(Authorities.USER)

                //Todo: 카테고리 권한 주석 제거
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/category/**").hasAnyAuthority(Authorities.USER)
//                .antMatchers(HttpMethod.POST, "/api/category/**").hasAnyAuthority(Authorities.ADMIN)
//                .antMatchers(HttpMethod.PUT, "/api/category/**").hasAnyAuthority(Authorities.ADMIN)
//                .antMatchers(HttpMethod.DELETE, "/api/category/**").hasAnyAuthority(Authorities.ADMIN)
                .antMatchers(HttpMethod.POST, "/api/category/**").hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.PUT, "/api/category/**").hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.DELETE, "/api/category/**").hasAnyAuthority(Authorities.USER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/product/**").hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.POST, "/api/product/**").hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.PUT, "/api/product/**").hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.DELETE, "/api/product/**").hasAnyAuthority(Authorities.SELLER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/product_image/**").hasAnyAuthority(Authorities.SELLER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/zzim/**").hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.POST, "/api/zzim/**").hasAnyAuthority(Authorities.USER)
                .antMatchers(HttpMethod.DELETE, "/api/zzim/**").hasAnyAuthority(Authorities.USER)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/shops/**").hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.POST, "/api/shops/**").hasAnyAuthority(Authorities.ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/shops/**").hasAnyAuthority(Authorities.SELLER)
                .antMatchers(HttpMethod.DELETE, "/api/shops/**").hasAnyAuthority(Authorities.ADMIN)

                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/login");
    }
}
