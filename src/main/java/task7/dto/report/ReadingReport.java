package task7.dto.report;

import lombok.*;
import task7.dto.MeterDto;
import task7.model.Meter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReadingReport {

    private MeterDto meter;
    private int minReading;
    private int maxReading;
    private int consumption;
}
