package peaksoft.instagram.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Username бош болбосун")
    private String userName;
    @Email(message = "Email туура эмес форматта")
    @NotBlank(message = "Email бош болбосун")
    private String email;
    @Pattern(regexp = "\\++996\\d{9}", message = "Телефон номери +996XXXXXXXXX форматта болушу керек")
    private String phoneNumber;
    @NotBlank(message = "Password бош болбосун")
    private String password;
}