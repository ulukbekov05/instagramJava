package peaksoft.instagram.dto.follower;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserShortResponse {
    private Long id;
    private String userName;
    private String email;
    private String image;
}