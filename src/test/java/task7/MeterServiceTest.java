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
import task7.dto.mapper.DtoMapper;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.service.MainService;
import task7.service.MeterGroupService;
import task7.service.MeterReadingService;
import task7.service.MeterService;

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
    @Autowired
    private DtoMapper dtoMapper;

    private MeterGroup group;
    private MeterGroupDto groupDto;
    private Meter meter;
    private MeterDto meterDto;

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
        group = new MeterGroup(1L, "group1");
        groupDto = meterGroupService.save(group, group.getId());
        meter = new Meter(1L, "type1", group);
        meterDto = meterService.save(meter, meter.getId());
    }

    @Test
    public void save() throws Exception {
        meter = new Meter(2L, "type1", group);
        meterDto = meterService.save(meter);
        assertEquals(meterDto, dtoMapper.toDTO(meter));
    }

    @Test
    public void findById() throws Exception {
        meterDto = meterService.findById(meter.getId());
        assertEquals(meterDto, dtoMapper.toDTO(meter));
    }

    @Test
    public void findAll() throws Exception {
        List<MeterDto> meterDtosResponse = meterService.findAll();
        List<MeterDto> meterDtos = List.of(dtoMapper.toDTO(meter));
        assertEquals(meterDtosResponse, meterDtos);
    }

    @Test
    public void update() throws Exception {
        meter.setType("newType");
        meterDto = meterService.update(meter);
        assertEquals(meterDto, dtoMapper.toDTO(meter));
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
