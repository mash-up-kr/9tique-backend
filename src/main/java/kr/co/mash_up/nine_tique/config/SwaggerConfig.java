package kr.co.mash_up.nine_tique.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * API 명세서를 만들어주는 Swagger 라이브러리 설정
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("kr.co.mash-up.9tique")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())  // 현재 RequestMapping으로 할당된 모든 url 리스트 추출
                .paths(PathSelectors.ant("/api/**"))  // '/api'로 시작하는 것만 문서화
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("구티크")
                .description("Mash-Up 2기(2016-2) Team Project")
                .termsOfServiceUrl("https://github.com/mash-up-kr/9tique-backend")
                .contact(new Contact("opklnm102", "https://github.com/opklnm102", "opklnm102@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0")
                .build();
    }
}
