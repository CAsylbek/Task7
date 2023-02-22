package task7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MeterGroupDto {

    @Positive
    private Long id;
    @NotBlank
    private String name;
}
