package task7.dao.jdbcTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import task7.dao.mapper.MeterReadingExtractor;
import task7.model.MeterReading;

import java.util.List;

@Component
public class MeterReadingDaoJdbcTemplate {

    private JdbcTemplate jdbcTemplate;
    private MeterReadingExtractor meterReadingExtractor;

    public MeterReadingDaoJdbcTemplate(JdbcTemplate jdbcTemplate,
                                       MeterReadingExtractor meterReadingExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.meterReadingExtractor = meterReadingExtractor;
    }

    public MeterReading save(MeterReading meterReading) {
        String query = "insert into meter_reading(current_reading, time_stamp, meter) values (?, ?, ?)";
        return saveMain(meterReading, query);
    }

    public MeterReading save(MeterReading meterReading, Long id) {
        String query = String.format("insert into meter_reading(id, current_reading, time_stamp, meter) values (%o, ?, ?, ?)", id);
        return saveMain(meterReading, query);
    }

    private MeterReading saveMain(MeterReading meterReading, String query) {
        Object[] args = new Object[]{
             meterReading.getCurrentReading(),
             meterReading.getTime(),
             meterReading.getMeter().getId()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meterReading;
        }
        return null;
    }

    public List<MeterReading> getAll() {
        String query = "select * from meter_reading";
        return jdbcTemplate.query(query, meterReadingExtractor);
    }

    public MeterReading getById(Long id) {
        String query = "select * from meter_reading where id=?";
        return jdbcTemplate.query(query, meterReadingExtractor, id)
             .stream().findFirst().orElse(null);
    }

    public List<MeterReading> getMaxMinReadings() {
        String query = "select id, max(current_reading) as current_reading, time_stamp, meter " +
             "from meter_reading " +
             "group by meter " +
             "union " +
             "select id, min(current_reading), time_stamp, meter " +
             "from meter_reading " +
             "group by meter";
        List<MeterReading> readings = jdbcTemplate.query(query, meterReadingExtractor);
        return readings;
    }

    public MeterReading update(MeterReading meterReading) {
        String query = "update meter_reading set id=?, current_reading=?, time_stamp=?, meter=?";
        Object[] args = new Object[]{
             meterReading.getId(),
             meterReading.getCurrentReading(),
             meterReading.getTime(),
             meterReading.getMeter().getId()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meterReading;
        }
        return null;
    }

    public void deleteById(Long id) {
        String query = "delete from meter_reading where id=?";
        jdbcTemplate.update(query, id);
    }

    public void deleteAll() {
        String query = "delete from meter_reading";
        jdbcTemplate.update(query);
    }
}
