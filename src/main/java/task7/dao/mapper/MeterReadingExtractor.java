package task7.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import task7.dao.jdbcTemplate.MeterDaoJdbcTemplate;
import task7.model.Meter;
import task7.model.MeterReading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MeterReadingExtractor implements ResultSetExtractor<List<MeterReading>> {

    private MeterDaoJdbcTemplate meterDaoJdbcTemplate;

    public MeterReadingExtractor(MeterDaoJdbcTemplate meterDaoJdbcTemplate) {
        this.meterDaoJdbcTemplate = meterDaoJdbcTemplate;
    }

    @Override
    public List<MeterReading> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<MeterReading> readings = new ArrayList<>();
        Map<Long, List<MeterReading>> idReadingsMap = new HashMap<>();

        while (rs.next()) {
            MeterReading reading = new MeterReading();
            reading.setId(rs.getLong("id"));
            reading.setCurrentReading(rs.getInt("current_reading"));
            reading.setTime(rs.getTimestamp("time_stamp"));
            Long meterId = rs.getLong("meter");
            idReadingsMap.computeIfAbsent(meterId, r -> new ArrayList<>()).add(reading);
        }

        List<Meter> meters = meterDaoJdbcTemplate.getListByIds(List.copyOf(idReadingsMap.keySet()));

        for (Long meterId : idReadingsMap.keySet()) {
            Meter meter = meters.stream().filter(m -> m.getId().equals(meterId)).findFirst().orElse(null);
            for (MeterReading reading : idReadingsMap.get(meterId)) {
                reading.setMeter(meter);
                readings.add(reading);
            }
        }

        return readings;
    }
}
