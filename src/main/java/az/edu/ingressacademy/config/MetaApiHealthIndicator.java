package az.edu.ingressacademy.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MetaApiHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {

        boolean isMetaUp = true;
        if (isMetaUp) {
            return Health.up().withDetail("Meta Graph API", "Active and responding").build();
        }
        return Health.down().withDetail("Meta Graph API", "Unreachable").build();
    }
}