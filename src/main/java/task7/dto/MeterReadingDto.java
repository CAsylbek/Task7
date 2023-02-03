package task7.dto;

import lombok.Data;
import task7.model.Meter;

@Data
public class MeterReadingDto {

    private Long id;
    private int minReading;
    private int maxReading;
    private int consumption;
    private Meter meter;
}
