package task7.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UsernamePasswordDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
