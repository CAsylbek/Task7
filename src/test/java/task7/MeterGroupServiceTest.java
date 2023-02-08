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
import task7.dto.MeterGroupDto;
import task7.dto.mapper.DtoMapper;
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
public class MeterGroupServiceTest {

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

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
        group = new MeterGroup(1L, "group1");
        groupDto = meterGroupService.save(group, group.getId());
    }

    @Test
    public void save() throws Exception {
        group = new MeterGroup(2L, "group1");
        groupDto = meterGroupService.save(group);
        assertEquals(groupDto, dtoMapper.toDTO(group));
    }

    @Test
    public void findById() throws Exception {
        groupDto = meterGroupService.findById(group.getId());
        assertEquals(groupDto, dtoMapper.toDTO(group));
    }

    @Test
    public void findAll() throws Exception {
        List<MeterGroupDto> groupDtosResponse = meterGroupService.findAll();
        List<MeterGroupDto> groupDtos = List.of(dtoMapper.toDTO(group));
        assertEquals(groupDtosResponse, groupDtos);
    }

    @Test
    public void update() throws Exception {
        group.setName("newName");
        groupDto = meterGroupService.update(group);
        assertEquals(groupDto, dtoMapper.toDTO(group));
    }

    @Test
    public void deleteById() throws Exception {
        meterGroupService.deleteById(group.getId());
        assertEquals(meterGroupService.findAll().size(), 0);
    }

    @Test
    public void deleteAll() throws Exception {
        meterGroupService.deleteAll();
        assertEquals(meterGroupService.findAll().size(), 0);
    }
}
