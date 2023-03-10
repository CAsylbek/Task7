package task7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import task7.model.Role;
import task7.model.User;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {

    @Positive
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private Set<String> roles;
}