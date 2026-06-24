package az.edu.ingressacademy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "meta.graph")
public record MetaProperties(
        String baseUrl,
        String accessToken,
        String pageId,
        int postLimit
) {}

