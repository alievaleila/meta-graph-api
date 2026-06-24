package az.edu.ingressacademy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MetaApiResponse {

    public record PostsPage(
            List<MetaPost> data,
            Paging paging
    ) {}

    public record MetaPost(
            String id,
            String message,
            @JsonProperty("created_time") String createdTime,
            MetricWrapper likes,
            MetricWrapper comments
    ) {}

    public record MetricWrapper(Summary summary) {}

    public record Summary(
            @JsonProperty("total_count") int totalCount
    ) {}

    public record Paging(
            Cursors cursors,
            String next
    ) {}

    public record Cursors(
            String before,
            String after
    ) {}
}