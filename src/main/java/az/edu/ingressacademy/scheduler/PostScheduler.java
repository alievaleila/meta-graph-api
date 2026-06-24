package az.edu.ingressacademy.scheduler;

import az.edu.ingressacademy.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class PostScheduler {

    private final PostService postService;

    @Scheduled(cron = "${scheduler.cron}")
    public void syncPosts() {
        log.info("Scheduled synchronization is starting...");
        try {
            int count = postService.syncPostsFromMeta();
            log.info("Scheduled synchronization completed: {} posts updated.", count);
        } catch (Exception ex) {

            log.error("Error during scheduled synchronization: {}", ex.getMessage());
        }
    }
}