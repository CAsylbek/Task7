package task7.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import task7.dao.jdbcTemplate.MeterDaoJdbcTemplate;
import task7.model.MeterReading;
import task7.service.MeterService;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MeterReadingMapper implements RowMapper<MeterReading> {

    private MeterDaoJdbcTemplate meterDaoJdbcTemplate;

    public MeterReadingMapper(MeterDaoJdbcTemplate meterDaoJdbcTemplate) {
        this.meterDaoJdbcTemplate = meterDaoJdbcTemplate;
    }

    @Override
    public MeterReading mapRow(ResultSet rs, int rowNum) throws SQLException {
        MeterReading meterReading = new MeterReading();
        meterReading.setId(rs.getLong("id"));
        meterReading.setMinReading(rs.getInt("min_reading"));
        meterReading.setMaxReading(rs.getInt("max_reading"));
        meterReading.setConsumption(rs.getInt("consumption"));
//        meterReading.setMeterID(rs.getLong("meter_id"));
        Long id = rs.getLong("meter");
        meterReading.setMeter(meterDaoJdbcTemplate.getById(id));
        return meterReading;
    }
}
