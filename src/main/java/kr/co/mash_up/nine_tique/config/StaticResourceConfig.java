package kr.co.mash_up.nine_tique.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 정적 리소스 매칭
 * ex) 127.0.0.1:8080/storage/product -> classpath:/storage/product 로 매칭
 * http://jsonobject.tistory.com/221
 */
@Configuration
public class StaticResourceConfig extends WebMvcConfigurerAdapter {

    @Value(value = "${static.resource.location}")
    private String staticResourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!registry.hasMappingForPattern("/storage/**")){
            registry.addResourceHandler("/storage/**")
                    .addResourceLocations(staticResourceLocation);
        }
    }
}
