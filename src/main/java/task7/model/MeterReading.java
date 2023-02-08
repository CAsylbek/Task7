package task7.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeterReading {

    private Long id;
    private int currentReading;
    private Timestamp time;
    private Meter meter;

    public MeterReading() {
    }

    public MeterReading(int currentReading, Timestamp time, Meter meter) {
        this.currentReading = currentReading;
        this.time = time;
        this.meter = meter;
    }

    public MeterReading(Long id, int currentReading, Timestamp time, Meter meter) {
        this(currentReading, time, meter);
        this.id = id;
    }
}
