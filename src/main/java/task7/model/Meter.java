package task7.model;

import lombok.Data;

@Data
public class Meter {

    private Long id;
    private String type;
    private MeterGroup meterGroup;

    public Meter() {
    }

    public Meter(String type, MeterGroup meterGroup) {
        this.type = type;
        this.meterGroup = meterGroup;
    }

    public Meter(Long id, String type, MeterGroup meterGroup) {
        this(type, meterGroup);
        this.id = id;
    }
}
