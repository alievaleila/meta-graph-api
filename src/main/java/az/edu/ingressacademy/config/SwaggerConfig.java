package az.edu.ingressacademy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Meta Post Analyzer API")
                        .version("1.0.0")
                        .description("Backend system for fetching, analyzing, and managing posts from the Meta Graph API.")
                        .contact(new Contact()
                                .name("Leyla")
                                .email("leyla@gmail.com")));
    }
}