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
import task7.model.MeterGroup;

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

    private MeterGroup group;
    private MeterGroup group2;

    @Before
    public void setup() {
        meterReadingService.deleteAll();
        meterService.deleteAll();
        meterGroupService.deleteAll();
        group = meterGroupService.save(new MeterGroup( "group1"));
    }

    @Test
    public void save() throws Exception {
        group = new MeterGroup( "group1");
        group2 = meterGroupService.save(group);
        assertEquals(group2, group);
    }

    @Test
    public void findById() throws Exception {
        group2 = meterGroupService.findById(group.getId());
        assertEquals(group2, group);
    }

    @Test
    public void findAll() throws Exception {
        List<MeterGroup> groupsResponse = meterGroupService.findAll();
        List<MeterGroup> groups = List.of(group);
        assertEquals(groupsResponse, groups);
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
