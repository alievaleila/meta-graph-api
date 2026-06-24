package az.edu.ingressacademy.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(MetaProperties.class)
public class AppConfig {

    @Bean
    public RestClient metaRestClient(MetaProperties metaProperties) {
        return RestClient.builder()
                .baseUrl(metaProperties.baseUrl())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}