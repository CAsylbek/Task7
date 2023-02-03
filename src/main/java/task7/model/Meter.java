package task7.model;

import lombok.Data;

@Data
public class Meter {

    private Long id;
    private String name;
    //todo: хранить объект или его id?
    private MeterGroup meterGroup;
}
