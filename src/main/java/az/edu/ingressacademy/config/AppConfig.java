package az.edu.ingressacademy.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
@EnableConfigurationProperties(MetaProperties.class)
public class AppConfig {

    @Bean
    public RestClient metaRestClient(MetaProperties metaProperties) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_JSON,
                new MediaType("text", "javascript"),
                MediaType.ALL
        ));

        return RestClient.builder()
                .baseUrl(metaProperties.baseUrl())
                .defaultHeader("Accept", "application/json, text/javascript, */*")
                .messageConverters(converters -> {
                    converters.clear();
                    converters.add(converter);
                })
                .build();
    }
}