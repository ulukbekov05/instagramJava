package peaksoft.instagram.dto.userDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String userName;
    private String email;
    private String phoneNumber;
}
