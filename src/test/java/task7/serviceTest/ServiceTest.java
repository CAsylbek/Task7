package task7.serviceTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task7.dto.PostDataJson;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ServiceTest {

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

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
    }

    @Test
    public void postReadingFromJson() throws Exception {
        Timestamp time = new Timestamp(2023, 1, 1, 1, 30, 0, 0);

        PostDataJson postDataJson = new PostDataJson(
             10L, "type10", "group10", time, 15);
        MeterReading readingDtoResponse = mainService.postReadingFromJson(postDataJson);

        MeterGroup group = meterGroupService.findByName("group10").stream().findFirst().orElse(null);

        MeterReading reading = new MeterReading();
        reading.setId(null);
        reading.setMeter(new Meter(10L, "type10", group));
        reading.setTime(time);
        reading.setCurrentReading(15);
        assertEquals(readingDtoResponse, reading);
    }
}
