package kr.co.mash_up.nine_tique.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
public class SystemPropertiesConfig {

    public static final String STORAGE_PATH = "STORAGE_PATH";
    public static final String STORAGE_URI = "STORAGE_URI";

    @Autowired
    private Environment env;

    @Bean
    public MethodInvokingFactoryBean systemPropertiesBean() {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setTargetObject(System.getProperties());
        bean.setTargetMethod("putAll");

        Properties properties = new Properties();
        properties.setProperty(STORAGE_PATH, env.getProperty("storage.path"));
        properties.setProperty(STORAGE_URI, env.getProperty("storage.uri"));
        bean.setArguments(new Object[]{properties});
        return bean;
    }

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
        return (ConfigurableEmbeddedServletContainer container) -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers(connector -> {
                    connector.setMaxPostSize(10000000);  // 10MB
                });
            }
        };
    }
}
