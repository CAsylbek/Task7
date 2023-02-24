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
import task7.model.MeterReading;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class MeterReadingServiceTest {

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
    private MeterReading meterReading;
    private MeterReading meterReading2;
    private Timestamp time = new Timestamp(2023, 1, 1, 1, 30, 0, 0);

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
        group = meterGroupService.save(new MeterGroup(1L, "group1"));
        meter = new Meter(1L, "type1", group);
        meterService.save(meter);
        meterReading = new MeterReading(1L, 15, time, meter);
        meterReadingService.save(meterReading);
    }

    @Test
    public void save() throws Exception {
        meterReading = new MeterReading(2L, 20, time, meter);
        meterReading2 = meterReadingService.save(meterReading);
        assertEquals(meterReading2, meterReading);
    }

    @Test
    public void findById() throws Exception {
        meterReading2 = meterReadingService.findById(meterReading.getId());
        assertEquals(meterReading2, meterReading);
    }

    @Test
    public void findAll() throws Exception {
        List<MeterReading> meterReadingsResponse = meterReadingService.findAll();
        List<MeterReading> meterReadings = List.of(meterReading);
        assertEquals(meterReadingsResponse, meterReadings);
    }

    @Test
    public void deleteById() throws Exception {
        meterReadingService.deleteById(meterReading.getId());
        assertEquals(meterReadingService.findAll().size(), 0);
    }

    @Test
    public void deleteAll() throws Exception {
        meterReadingService.deleteById(meterReading.getId());
        assertEquals(meterReadingService.findAll().size(), 0);
    }
}
