package az.edu.ingressacademy.mapper;

import az.edu.ingressacademy.dto.response.MetaApiResponse;
import az.edu.ingressacademy.dto.response.PostResponse;
import az.edu.ingressacademy.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostMapper {

    private static final DateTimeFormatter META_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public static Post toEntity(MetaApiResponse.MetaPost metaPost) {
        int likes = metaPost.likes() != null ? metaPost.likes().summary().totalCount() : 0;
        int comments = metaPost.comments() != null ? metaPost.comments().summary().totalCount() : 0;

        return Post.builder()
                .postId(metaPost.id())
                .message(metaPost.message())
                .likesCount(likes)
                .commentsCount(comments)
                .createdAt(ZonedDateTime.parse(metaPost.createdTime(), META_DATE_FORMAT)
                        .toLocalDateTime())
                .build();
    }

    public static PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .postId(post.getPostId())
                .message(post.getMessage())
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .engagement(post.getLikesCount() + post.getCommentsCount())
                .createdAt(post.getCreatedAt())
                .build();
    }
}