package az.edu.ingressacademy.service;

import az.edu.ingressacademy.dto.response.AnalysisResponse;
import az.edu.ingressacademy.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    int syncPostsFromMeta();

    List<PostResponse> getAllPosts();

    List<PostResponse> getTop3Posts();

    AnalysisResponse getAnalysis();
}