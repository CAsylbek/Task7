package task7.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "meter_reading", schema = "meters_data_8_test")
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private int currentReading;
    private Timestamp time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "METER_ID")
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
