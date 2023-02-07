package task7.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeterReading {

    private Long id;
    private int currentReading;
    private Timestamp time;
    private Meter meter;
}
