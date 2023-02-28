package task7.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task7.AbstractTest;
import task7.model.Meter;
import task7.model.MeterGroup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup("/dbunitSource/meterData.xml")
public class MeterServiceTest extends AbstractTest {

    @Autowired
    private MeterService meterService;

    @Test
    public void save() throws Exception {
        MeterGroup group = new MeterGroup(1L, "first group");
        Meter meter = new Meter("meter 1", group);
        Meter meter2 = meterService.save(meter);
        assertEquals(meter, meter2);
    }

    @Test
    public void findById() throws Exception {
        Meter meter2= meterService.findById(1L);
        assertEquals(meter2.getId(), 1L);
        assertEquals(meter2.getType(), "meter 1");
        assertEquals(meter2.getMeterGroup().getId(), 1L);
    }

    @Test
    public void findAll() throws Exception {
        List<Meter> meters2 = meterService.findAll();
        assertEquals(meters2.size(), 3);
    }

    @Test
    public void deleteById() throws Exception {
        meterService.deleteById(1L);
        assertEquals(meterService.findAll().size(), 2);
    }

    @Test
    public void deleteAll() throws Exception {
        meterService.deleteAll();
        assertEquals(meterService.findAll().size(), 0);
    }
}
