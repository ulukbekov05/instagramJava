package peaksoft.instagram.dto.userInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.instagram.enums.Gender;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    @NotBlank(message = "full name бош болбосун")
    private String fullName;

    private String biography;

    @NotNull(message = "gender бош болбосун")
    private Gender gender;

    private String image;
}