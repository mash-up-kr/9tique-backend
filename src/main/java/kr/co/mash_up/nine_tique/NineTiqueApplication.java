package kr.co.mash_up.nine_tique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
/*
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
}
