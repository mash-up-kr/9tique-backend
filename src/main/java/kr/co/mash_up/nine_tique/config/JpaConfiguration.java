package kr.co.mash_up.nine_tique.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by ethan.kim on 2018. 7. 31..
 */
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
@Configuration
public class JpaConfiguration {

    @Bean(name = "userAuditorAware")
    public AuditorAware userAuditorAware() {
        return new UserAuditorAware();
    }
}
