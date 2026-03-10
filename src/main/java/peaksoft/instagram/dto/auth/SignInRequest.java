package peaksoft.instagram.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest{
    @NotBlank(message = "Email бош болбосун")
    private  String email;
    @NotBlank(message = "password бош болбосун")
    private String password;
}
