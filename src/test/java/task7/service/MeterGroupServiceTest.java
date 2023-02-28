package task7.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task7.AbstractTest;
import task7.model.MeterGroup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup("/dbunitSource/meterData.xml")
public class MeterGroupServiceTest extends AbstractTest {

    @Autowired
    private MeterGroupService meterGroupService;

    @Test
    public void save() throws Exception {
        MeterGroup group = new MeterGroup( "group1");
        MeterGroup group2 = meterGroupService.save(group);
        assertEquals(group, group2);
    }

    @Test
    public void findById() throws Exception {
        MeterGroup group = meterGroupService.findById(1L);
        assertEquals(group.getId(), 1L);
        assertEquals(group.getName(), "first group");
        assertEquals(group.getMeters().size(), 2);
    }

    @Test
    public void findAll() throws Exception {
        List<MeterGroup> groups = meterGroupService.findAll();
        assertEquals(groups.size(), 2);
    }

    @Test
    public void deleteById() throws Exception {
        meterGroupService.deleteById(1L);
        assertEquals(meterGroupService.findAll().size(), 1);
    }

    @Test
    public void deleteAll() throws Exception {
        meterGroupService.deleteAll();
        assertEquals(meterGroupService.findAll().size(), 0);
    }
}
