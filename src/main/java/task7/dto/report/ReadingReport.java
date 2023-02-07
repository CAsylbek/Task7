package task7.dto.report;

import lombok.Data;
import task7.model.Meter;

@Data
public class ReadingReport {

    private Meter meter;
    private int minReading;
    private int maxReading;
    private int consumption;
}
