package peaksoft.instagram.dto.userInfo;

import lombok.Builder;
import lombok.Data;
import peaksoft.instagram.enums.Gender;
@Data
@Builder

public class UserInfoResponse {
    private String fullName;
    private String biography;
    private Gender gender;
    private String image;
}
