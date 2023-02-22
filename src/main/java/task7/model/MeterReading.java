package task7.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int currentReading;
    private Timestamp time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id")
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
