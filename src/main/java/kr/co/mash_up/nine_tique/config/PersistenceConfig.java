package kr.co.mash_up.nine_tique.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * Created by ethankim on 2017. 8. 6..
 */
@Configuration
public class PersistenceConfig {

    @Bean
    public JPAQueryFactory queryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
