package task7.model;

import lombok.Data;

@Data
public class MeterReading {

    private Long id;
    private int minReading;
    private int maxReading;
    private int consumption;
    private Meter meter;
}
