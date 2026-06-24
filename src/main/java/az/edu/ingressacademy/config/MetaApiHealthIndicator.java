package az.edu.ingressacademy.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MetaApiHealthIndicator implements HealthIndicator {

    private final RestClient healthRestClient;

    public MetaApiHealthIndicator() {

        this.healthRestClient = RestClient.builder()
                .baseUrl("https://graph.facebook.com")
                .build();
    }

    @Override
    public Health health() {
        try {

            healthRestClient.get().uri("/v19.0").retrieve().toBodilessEntity();

            return Health.up()
                    .withDetail("Meta Graph API", "Server is reachable")
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail("Meta Graph API", "Unreachable")
                    .withDetail("Reason", ex.getMessage())
                    .build();
        }
    }
}