package peaksoft.instagram.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import peaksoft.instagram.validation.Password;
import peaksoft.instagram.validation.UserName;

@Data
public class SignUpRequest {
        @NotBlank(message = "Username бош болбосун")
        @UserName
        String userName;
        @Password
        String password;
        @Email(message = "Email туура эмес форматта")
        @NotBlank(message = "Email бош болбосун")
        String email;
        @Pattern(regexp = "\\+996\\d{9}", message = "Телефон номери +996XXXXXXXXX форматта болушу керек")
        String phoneNumber;


}
