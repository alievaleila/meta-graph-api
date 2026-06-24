package az.edu.ingressacademy.dto.response;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record AnalysisResponse(

        List<PostResponse> top3Posts,
        Map<String, Double> avgLikesByDay,
        String bestDayToPost,
        String conclusion,
        int totalPostsAnalyzed
) {}