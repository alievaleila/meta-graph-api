package az.edu.ingressacademy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Server productionServer = new Server();
        productionServer.setUrl("https://meta-graph-api-production-5b10.up.railway.app");
        productionServer.setDescription("Production server (Secure)");


        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local development server");

        return new OpenAPI()
                .servers(List.of(productionServer, localServer))
                .info(new Info()
                        .title("Meta Post Analyzer API")
                        .version("1.0.0")
                        .description("Backend system for fetching, analyzing, and managing posts from the Meta Graph API.")
                        .contact(new Contact()
                                .name("Leyla")
                                .email("leyla@gmail.com")));
    }
}