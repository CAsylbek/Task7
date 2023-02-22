package task7.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeterReadingDto {

    @Positive
    private Long id;
    @Positive
    private int currentReading;
    @NotEmpty
    private Timestamp time;
    @Positive
    private MeterDto meter;
}
