package az.edu.ingressacademy.client;

import az.edu.ingressacademy.config.MetaProperties;
import az.edu.ingressacademy.dto.response.MetaApiResponse;
import az.edu.ingressacademy.exception.MetaApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetaGraphClient {

    private final RestClient metaRestClient;
    private final MetaProperties metaProperties;

    public MetaApiResponse.PostsPage fetchPosts() {
        String fields = "id,message,created_time,likes.summary(true),comments.summary(true)";

        log.info("Fetching {} posts from Meta API. PageId: {}",
                metaProperties.postLimit(), metaProperties.pageId());

        try {
            MetaApiResponse.PostsPage response = metaRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + metaProperties.pageId() + "/posts")
                            .queryParam("fields", fields)
                            .queryParam("limit", metaProperties.postLimit())
                            .queryParam("access_token", metaProperties.accessToken())
                            .build())
                    .retrieve()
                    .body(MetaApiResponse.PostsPage.class);

            if (response == null || response.data() == null) {
                throw new MetaApiException("Meta API returned an empty response");
            }

            log.info("Successfully fetched {} posts from Meta API", response.data().size());
            return response;

        } catch (RestClientException ex) {
            log.error("An error occurred while calling Meta API: {}", ex.getMessage());
            throw new MetaApiException("Failed to connect to Meta API", ex);
        }
    }
}