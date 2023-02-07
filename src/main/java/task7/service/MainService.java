package task7.service;

import org.springframework.stereotype.Service;
import task7.dao.jdbcTemplate.MeterDaoJdbcTemplate;
import task7.dao.jdbcTemplate.MeterGroupDaoJdbcTemplate;
import task7.dao.jdbcTemplate.MeterReadingDaoJdbcTemplate;
import task7.dto.MeterReadingDto;
import task7.dto.PostDataJson;
import task7.dto.mapper.DtoMapper;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;

import java.util.List;

@Service
public class MainService {

    private MeterDaoJdbcTemplate meterDaoJdbcTemplate;
    private MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate;
    private MeterReadingDaoJdbcTemplate meterReadingDaoJdbcTemplate;
    private DtoMapper dtoMapper;

    public MainService(DtoMapper dtoMapper,
                       MeterDaoJdbcTemplate meterDaoJdbcTemplate,
                       MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate,
                       MeterReadingDaoJdbcTemplate meterReadingDaoJdbcTemplate) {
        this.meterDaoJdbcTemplate = meterDaoJdbcTemplate;
        this.meterGroupDaoJdbcTemplate = meterGroupDaoJdbcTemplate;
        this.meterReadingDaoJdbcTemplate = meterReadingDaoJdbcTemplate;
        this.dtoMapper = dtoMapper;
    }

    public MeterReadingDto postReadingFromJson(PostDataJson reportJson) throws Exception {
        MeterReading reading = new MeterReading();

        System.out.println(reportJson);

        Long id = reportJson.getMeterId();
        Meter meter = meterDaoJdbcTemplate.getById(id);
        String type = reportJson.getType();
        String groupName = reportJson.getMeterGroup();
        List<MeterGroup> groups = meterGroupDaoJdbcTemplate.getByName(groupName);

        if (!meter.getType().equals(type) || !groups.contains(meter.getMeterGroup())) {
            throw new Exception("Type and group do not match");
        }

        reading.setMeter(meter);
        reading.setCurrentReading(reportJson.getCurrentReading());
        reading.setTime(reportJson.getTimeStamp());
        meterReadingDaoJdbcTemplate.save(reading);

        return dtoMapper.toDTO(reading);
    }
}
