package task7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import task7.model.MeterGroup;

@Data
public class MeterDto {

    @Positive
    private Long id;
    @NotBlank
    private String type;
    @Positive
    private MeterGroup meterGroup;
}
