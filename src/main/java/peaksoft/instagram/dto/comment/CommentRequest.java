package peaksoft.instagram.dto.comment;

import lombok.Data;

@Data
public class CommentRequest {
    private String comment;
    private Long postId;
}
