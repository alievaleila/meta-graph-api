package az.edu.ingressacademy.controller;

import az.edu.ingressacademy.dto.response.AnalysisResponse;
import az.edu.ingressacademy.dto.response.PostResponse;
import az.edu.ingressacademy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/sync")
    public ResponseEntity<String> syncPosts() {
        int count = postService.syncPostsFromMeta();
        return ResponseEntity.ok("%d posts successfully synchronized.".formatted(count));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/top")
    public ResponseEntity<List<PostResponse>> getTop3Posts() {
        return ResponseEntity.ok(postService.getTop3Posts());
    }

    @GetMapping("/analysis")
    public ResponseEntity<AnalysisResponse> getAnalysis() {
        return ResponseEntity.ok(postService.getAnalysis());
    }
}