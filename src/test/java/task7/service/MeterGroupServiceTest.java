package task7.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import task7.model.MeterGroup;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@TestExecutionListeners({
     TransactionalTestExecutionListener.class,
     DependencyInjectionTestExecutionListener.class,
     DbUnitTestExecutionListener.class})
@DatabaseSetup("/meterDataTest.xml")
public class MeterGroupServiceTest {

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


    @Autowired
    private DataSource dataSource;
    private DataSourceDatabaseTester dataSourceDatabaseTester;
    private IDataSet dataSet;

//    @Before
//    public void setup() {
//        meterReadingService.deleteAll();
//        meterService.deleteAll();
//        meterGroupService.deleteAll();
//        group = meterGroupService.save(new MeterGroup( "group1"));
//    }

//    @Before
//    public void setup() throws Exception {
//        dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSource);
//        dataSet = new FlatXmlDataSetBuilder().build(getClass().getResource("meterDataTest.xml"));
//        dataSourceDatabaseTester.setSchema("meters_data_8_test");
//        dataSourceDatabaseTester.setDataSet(dataSet);
//        dataSourceDatabaseTester.onSetup();
//
//    }

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
        groupsResponse.forEach(g -> System.out.println(g.getName()));
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
