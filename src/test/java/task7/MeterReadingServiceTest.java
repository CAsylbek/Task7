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
import task7.dto.MeterDto;
import task7.dto.MeterGroupDto;
import task7.dto.MeterReadingDto;
import task7.dto.mapper.DtoMapper;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;
import task7.service.MainService;
import task7.service.MeterGroupService;
import task7.service.MeterReadingService;
import task7.service.MeterService;

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
    @Autowired
    private DtoMapper dtoMapper;

    private MeterGroup group;
    private MeterGroupDto groupDto;
    private Meter meter;
    private MeterDto meterDto;
    private MeterReading meterReading;
    private MeterReadingDto meterReadingDto;
    private Timestamp time = new Timestamp(2023, 1, 1, 1, 30, 0, 0);

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
        group = new MeterGroup(1L, "group1");
        groupDto = meterGroupService.save(group, group.getId());
        meter = new Meter(1L, "type1", group);
        meterDto = meterService.save(meter, meter.getId());
        meterReading = new MeterReading(1L, 15, time, meter);
        meterReadingDto = meterReadingService.save(meterReading, meterReading.getId());
    }

    @Test
    public void save() throws Exception {
        meterReading = new MeterReading(2L, 20, time, meter);
        meterReadingDto = meterReadingService.save(meterReading);
        assertEquals(meterReadingDto, dtoMapper.toDTO(meterReading));
    }

    @Test
    public void findById() throws Exception {
        meterReadingDto = meterReadingService.findById(meterReading.getId());
        assertEquals(meterReadingDto, dtoMapper.toDTO(meterReading));
    }

    @Test
    public void findAll() throws Exception {
        List<MeterReadingDto> meterReadingDtosResponse = meterReadingService.findAll();
        List<MeterReadingDto> meterReadingDtos = List.of(dtoMapper.toDTO(meterReading));
        assertEquals(meterReadingDtosResponse, meterReadingDtos);
    }

    @Test
    public void update() throws Exception {
        meterReading.setCurrentReading(45);
        meterReadingDto = meterReadingService.update(meterReading);
        assertEquals(meterReadingDto, dtoMapper.toDTO(meterReading));
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
