package task7.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task7.AbstractTest;
import task7.model.Meter;
import task7.model.MeterReading;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup("/dbunitSource/meterData.xml")
public class  MeterReadingServiceTest extends AbstractTest {

    @Autowired
    private MeterReadingService meterReadingService;

    @Test
    public void save() throws Exception {
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setType("meter 1");
        MeterReading reading = new MeterReading(5, Timestamp.valueOf("2023-02-27 11:00:45.744851"), meter);
        MeterReading reading2 = meterReadingService.save(reading);
        assertEquals(reading, reading2);
    }

    @Test
    public void findById() throws Exception {
        MeterReading reading = meterReadingService.findById(1L);
        assertEquals(reading.getId(), 1L);
        assertEquals(reading.getCurrentReading(), 5);
        assertEquals(reading.getTime(), Timestamp.valueOf("2023-02-27 11:00:45.744851"));
        assertEquals(reading.getMeter().getId(), 1L);
    }

    @Test
    public void findAll() throws Exception {
        List<MeterReading> readings = meterReadingService.findAll();
        assertEquals(readings.size(), 6);
    }

    @Test
    public void deleteById() throws Exception {
        meterReadingService.deleteById(1L);
        assertEquals(meterReadingService.findAll().size(), 5);
    }

    @Test
    public void deleteAll() throws Exception {
        meterReadingService.deleteAll();
        assertEquals(meterReadingService.findAll().size(), 0);
    }
}
