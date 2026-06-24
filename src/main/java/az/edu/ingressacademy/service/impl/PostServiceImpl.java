package az.edu.ingressacademy.service.impl;

import az.edu.ingressacademy.client.MetaGraphClient;
import az.edu.ingressacademy.dto.response.AnalysisResponse;
import az.edu.ingressacademy.dto.response.MetaApiResponse;
import az.edu.ingressacademy.dto.response.PostResponse;
import az.edu.ingressacademy.entity.Post;
import az.edu.ingressacademy.mapper.PostMapper;
import az.edu.ingressacademy.repository.PostRepository;
import az.edu.ingressacademy.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MetaGraphClient metaGraphClient;

    @Override
    @Transactional
    public int syncPostsFromMeta() {
        MetaApiResponse.PostsPage postsPage = metaGraphClient.fetchPosts();
        List<MetaApiResponse.MetaPost> metaPosts = postsPage.data();

        int savedCount = 0;
        for (MetaApiResponse.MetaPost metaPost : metaPosts) {
            Post post = postRepository.findByPostId(metaPost.id())
                    .map(existing -> updateExisting(existing, metaPost))
                    .orElseGet(() -> PostMapper.toEntity(metaPost));

            postRepository.save(post);
            savedCount++;
        }

        log.info("Synchronization completed. {} posts processed.", savedCount);
        return savedCount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getTop3Posts() {
        return postRepository.findTop3ByEngagement()
                .stream()
                .map(PostMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AnalysisResponse getAnalysis() {
        List<PostResponse> top3 = getTop3Posts();
        Map<String, Double> avgLikesByDay = buildAvgLikesByDay();

        String bestDay = avgLikesByDay.isEmpty()
                ? "No data available"
                : avgLikesByDay.entrySet().iterator().next().getKey().trim();

        String conclusion = buildConclusion(top3, avgLikesByDay, bestDay);

        return AnalysisResponse.builder()
                .top3Posts(top3)
                .avgLikesByDay(avgLikesByDay)
                .bestDayToPost(bestDay)
                .conclusion(conclusion)
                .totalPostsAnalyzed((int) postRepository.count())
                .build();
    }

    private Post updateExisting(Post existing, MetaApiResponse.MetaPost metaPost) {
        int likes = metaPost.likes() != null ? metaPost.likes().summary().totalCount() : 0;
        int comments = metaPost.comments() != null ? metaPost.comments().summary().totalCount() : 0;
        existing.setLikesCount(likes);
        existing.setCommentsCount(comments);
        return existing;
    }

    private Map<String, Double> buildAvgLikesByDay() {
        return postRepository.findAvgLikesByDayOfWeek()
                .stream()
                .collect(Collectors.toMap(
                        row -> {
                            Object val = row[0];
                            if (val instanceof String s) return s.trim();

                            int dayNum = ((Number) val).intValue();
                            return switch (dayNum) {
                                case 1 -> "Sunday";
                                case 2 -> "Monday";
                                case 3 -> "Tuesday";
                                case 4 -> "Wednesday";
                                case 5 -> "Thursday";
                                case 6 -> "Friday";
                                case 7 -> "Saturday";
                                default -> "Invalid";
                            };
                        },
                        row -> ((Number) row[1]).doubleValue(),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    private String buildConclusion(List<PostResponse> top3,
                                   Map<String, Double> avgLikesByDay,
                                   String bestDay) {
        if (top3.isEmpty()) {
            return "Not enough data for analysis. Please call the /api/v1/posts/sync endpoint first.";
        }

        PostResponse topPost = top3.get(0);
        double maxAvgLikes = avgLikesByDay.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);

        return """
                📊 Analysis Result:
                • The highest-engagement post received %d likes and %d comments.
                • Best day to post: %s (average %.1f likes).
                • Average engagement of Top 3 posts: %.0f.
                """.formatted(
                topPost.likesCount(),
                topPost.commentsCount(),
                bestDay,
                maxAvgLikes,
                top3.stream().mapToInt(PostResponse::engagement).average().orElse(0)
        );
    }
}