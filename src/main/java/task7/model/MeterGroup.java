package task7.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class MeterGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "meterGroup", cascade = CascadeType.ALL)
    private List<Meter> meters = new ArrayList<>();

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
