package task7.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import task7.AbstractTest;
import task7.dto.PostDataJson;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseSetup("/dbunitSource/meterData.xml")
public class ServiceTest extends AbstractTest {

    @Autowired
    private MainService mainService;
    @Autowired
    private MeterService meterService;
    @Autowired
    private MeterGroupService meterGroupService;
    @Autowired
    private MeterReadingService meterReadingService;

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

    @Test
    public void excelFileToGroupReport() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/dbunitSource/report.xls"));
        mainService.saveExcelAsMeterReading(new ByteArrayResource(bytes));
        List<MeterReading> readings = meterReadingService.findAll();

        List<Meter> meters = meterService.findAll();
        List<MeterGroup> groups = meterGroupService.findAll();

        assertEquals(readings.size(), 12);
        assertEquals(groups.size(), 4);
        assertEquals(meters.size(), 6);
    }
}
