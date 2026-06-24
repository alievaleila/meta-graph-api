package az.edu.ingressacademy.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(

        Long id,
        String postId,
        String message,
        Integer likesCount,
        Integer commentsCount,
        Integer engagement,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {}