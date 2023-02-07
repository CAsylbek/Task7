package task7.model;

import lombok.Data;

@Data
public class Meter {

    private Long id;
    private String type;
    private MeterGroup meterGroup;
}
