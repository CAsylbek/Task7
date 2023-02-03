package task7.dto;

import lombok.Data;
import task7.model.MeterGroup;

@Data
public class MeterDto {

    private Long id;
    private String name;
    private MeterGroup meterGroup;
}
