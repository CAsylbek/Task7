package task7.dto.report;

import lombok.Data;
import task7.model.MeterGroup;

import java.sql.Timestamp;
import java.util.List;

@Data
public class GroupReport {

    private List<ReadingReport> readings;
    private Timestamp time;
    private MeterGroup meterGroup;
    private int consumption;
}
