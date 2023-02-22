package task7.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_group_id")
    private MeterGroup meterGroup;
    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<MeterReading> meterReadings = new ArrayList<>();

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
