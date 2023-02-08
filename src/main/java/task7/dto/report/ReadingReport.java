package task7.dto.report;

import lombok.Data;
import task7.model.Meter;

@Data
public class ReadingReport {

    private Meter meter;
    private int minReading;
    private int maxReading;
    private int consumption;

    public ReadingReport(Meter meter, int minReading, int maxReading, int consumption) {
        this.meter = meter;
        this.minReading = minReading;
        this.maxReading = maxReading;
        this.consumption = consumption;
    }

    public ReadingReport() {
    }
}
