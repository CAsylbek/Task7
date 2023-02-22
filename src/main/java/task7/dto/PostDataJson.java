package task7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PostDataJson {

    @NotNull
    private Long meterId;
    @NotBlank
    private String type;
    @NotBlank
    private String meterGroup;
    @NotNull
    private Timestamp timeStamp;
    @NotNull
    @Positive
    private int currentReading;
}
