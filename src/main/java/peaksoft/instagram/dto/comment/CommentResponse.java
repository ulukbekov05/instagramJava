package peaksoft.instagram.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String comment,
        LocalDateTime createdAt,
        String userName,
        int likeCount
) {}