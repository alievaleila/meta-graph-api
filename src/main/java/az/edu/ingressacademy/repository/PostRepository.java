package az.edu.ingressacademy.repository;

import az.edu.ingressacademy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostId(String postId);

    @Query("""
            SELECT p FROM Post p
            ORDER BY (p.likesCount + p.commentsCount) DESC
            LIMIT 3
            """)
    List<Post> findTop3ByEngagement();


    @Query(value = "SELECT EXTRACT(ISODOW FROM created_at) AS day, AVG(likes_count) " +
            "FROM posts GROUP BY day", nativeQuery = true)
    List<Object[]> findAvgLikesByDayOfWeek();


    List<Post> findAllByOrderByCreatedAtDesc();
}