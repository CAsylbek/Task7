package task7.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import task7.dto.MeterGroupDto;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupReport {

    private List<ReadingReport> readings;
    private Timestamp time;
    private MeterGroupDto meterGroup;
    private int consumption;
}
