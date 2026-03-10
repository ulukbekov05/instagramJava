package peaksoft.instagram.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
@Data
public class PostRequest {
    @NotBlank(message = "Title бош болбош керек")
    private String title;
    @NotBlank(message = "Description бош болбош керек")
    private String description;
    @NotBlank(message = "Сүрөт милдеттүү")
    private String imageURL;
   private List<Long> taggedUserIds ;
}
