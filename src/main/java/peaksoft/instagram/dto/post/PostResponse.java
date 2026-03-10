package peaksoft.instagram.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import peaksoft.instagram.dto.follower.UserShortResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String imageURL;
    private boolean isLike;
    private int likeCount;
    private List<UserShortResponse> taggedUsers;
}