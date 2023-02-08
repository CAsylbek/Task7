package task7.model;

import lombok.Data;

@Data
public class MeterGroup {

    private Long id;
    private String name;

    public MeterGroup() {
    }

    public MeterGroup(String name) {
        this.name = name;
    }

    public MeterGroup(Long id, String name) {
        this(name);
        this.id = id;
    }
}
