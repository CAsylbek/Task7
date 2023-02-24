package task7.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task7.model.Meter;
import task7.model.MeterGroup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class MeterServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainService mainService;
    @Autowired
    private MeterService meterService;
    @Autowired
    private MeterGroupService meterGroupService;
    @Autowired
    private MeterReadingService meterReadingService;

    private MeterGroup group;
    private Meter meter;
    private Meter meterDto2;

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
        group = meterGroupService.save(new MeterGroup(1L, "group1"));
        meterService.save(new Meter(1L, "type1", group));
    }

    @Test
    public void myTest() throws Exception {
        MeterGroup g = new MeterGroup("g1");
        Meter m = new Meter("m1", g);
        meterService.save(m);
        System.out.println(meterGroupService.findByName("g1"));
    }

    @Test
    public void save() throws Exception {
        meter = new Meter(2L, "type1", group);
        meterDto2 = meterService.save(meter);
        assertEquals(meterDto2, meter);
    }

    @Test
    public void findById() throws Exception {
        meterDto2 = meterService.findById(meter.getId());
        assertEquals(meterDto2, meter);
    }

    @Test
    public void findAll() throws Exception {
        List<Meter> metesResponse = meterService.findAll();
        List<Meter> meters = List.of(meter);
        assertEquals(metesResponse, meters);
    }


    @Test
    public void deleteById() throws Exception {
        meterService.deleteById(meter.getId());
        assertEquals(meterService.findAll().size(), 0);
    }

    @Test
    public void deleteAll() throws Exception {
        meterService.deleteById(meter.getId());
        assertEquals(meterService.findAll().size(), 0);
    }
}
