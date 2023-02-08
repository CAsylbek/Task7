package task7;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task7.dao.jdbcTemplate.MeterGroupDaoJdbcTemplate;
import task7.dto.MeterReadingDto;
import task7.dto.PostDataJson;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.service.MainService;
import task7.service.MeterGroupService;
import task7.service.MeterReadingService;
import task7.service.MeterService;

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
    private MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate;
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
        MeterReadingDto readingDtoResponse = mainService.postReadingFromJson(postDataJson);

        MeterGroup group = meterGroupDaoJdbcTemplate.getByName("group10").stream().findFirst().orElse(null);

        MeterReadingDto readingDto = new MeterReadingDto();
        readingDto.setId(null);
        readingDto.setMeter(new Meter(10L, "type10", group));
        readingDto.setTime(time);
        readingDto.setCurrentReading(15);
        assertEquals(readingDtoResponse, readingDto);
    }
}
