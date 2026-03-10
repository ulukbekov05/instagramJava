package peaksoft.instagram.dto.userProfile;

import lombok.Builder;
import lombok.Data;
import peaksoft.instagram.dto.userDto.PostResponse;

import java.util.List;

@Data
@Builder
public class UserProfileResponse {
    private String userName;
    private String image;
    private String fullName;
    private int subscribersCount;
    private int subscriptionsCount;
    private List<PostResponse> posts;
}