package kr.co.mash_up.nine_tique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/*
http://homoefficio.github.io/2016/11/19/Spring-Data-JPA-%EC%97%90%EC%84%9C-Java8-Date-Time-JSR-310-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0/
JPA에서 JAVA8의 LocalDateTime을 지원하지 않는다.
Jsr310JpaConverters에 static class로 LocalDateTimeConverter가 있다.
 */
@EntityScan(basePackageClasses = {NineTiqueApplication.class, Jsr310JpaConverters.class})
public class NineTiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(NineTiqueApplication.class, args);
        System.out.println("Ninetique Server Run!!");
        System.out.println("args = [" + args + "]");
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
