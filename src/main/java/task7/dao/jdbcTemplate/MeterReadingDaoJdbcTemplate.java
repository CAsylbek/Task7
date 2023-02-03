package task7.dao.jdbcTemplate;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import task7.dao.mapper.MeterMapper;
import task7.dao.mapper.MeterReadingMapper;
import task7.model.Meter;
import task7.model.MeterReading;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MeterReadingDaoJdbcTemplate {

    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper beanPropertyRowMapper;
    private MeterDaoJdbcTemplate meterDaoJdbcTemplate;
    private MeterReadingMapper meterReadingMapper;

    public MeterReadingDaoJdbcTemplate(JdbcTemplate jdbcTemplate,
                                       MeterDaoJdbcTemplate meterDaoJdbcTemplate,
                                       MeterReadingMapper meterReadingMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.beanPropertyRowMapper = new BeanPropertyRowMapper(Meter.class);
        this.meterDaoJdbcTemplate = meterDaoJdbcTemplate;
        this.meterReadingMapper = meterReadingMapper;
    }

    public MeterReading save(MeterReading meterReading) {
        String query = "insert into meter_reading(id, min_reading, max_reading, consumption, meter) values (?, ?, ?, ?, ?)";
        Object[] args = new Object[]{
             meterReading.getId(),
             meterReading.getMinReading(),
             meterReading.getMaxReading(),
             meterReading.getConsumption(),
             meterReading.getMeter().getId()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meterReading;
        }
        return null;
    }

    public List<MeterReading> getAll() {
        String query = "select id, min_reading, max_reading, consumption, meter from meter_reading";
        List<MeterReading> meterReadings = jdbcTemplate.query(query, meterReadingMapper);

//        List<Map<String, Object>> ReadingMaps = jdbcTemplate.queryForList(query);
//        List<Long> meterIds = new ArrayList<>();
//
//        List<Map<MeterReading, Long>> readingsIds = ReadingMaps.stream()
//             .map(map -> {
//                 MeterReading meterReading = new MeterReading();
//                 meterReading.setId(Long.parseLong(String.valueOf(map.get("id"))));
//                 meterReading.setMinReading(Integer.parseInt(String.valueOf(map.get("min_reading"))));
//                 meterReading.setMaxReading(Integer.parseInt(String.valueOf(map.get("max_reading"))));
//                 meterReading.setConsumption(Integer.parseInt(String.valueOf(map.get("consumption"))));
//                 Long id = Long.parseLong(String.valueOf(map.get("meter_id")));
//                 if (!meterIds.contains(id)) {
//                     meterIds.add(id);
//                 }
//                 return Map.of(meterReading, id);
//             }).toList();
//
//        List<Meter> meters = meterDaoJdbcTemplate.getListByIds(meterIds);
//
//        List<MeterReading> meterReadings = readingsIds.stream()
//             .map(m -> {
//                  m.forEach((r, id) -> {
//                     Meter meter = meters.stream().filter(met -> met.getId() == id).findFirst().get();
//                     r.setMeter(meter);
//                 });
//                 return r;
//             })
//             .toList();

        return meterReadings;
    }

    public MeterReading getById(Long id) {
        String query = "select id, min_reading, max_reading, consumption, meter from meter_reading where id=?";
        MeterReading meterReading = (MeterReading)jdbcTemplate.queryForObject(query, meterReadingMapper, id);
        return meterReading;
    }

    public MeterReading update(MeterReading meterReading) {
        String query = "update meter_reading set id=?, min_reading=?, max_reading=?, consumption=?, meter=?";
        Object[] args = new Object[]{
             meterReading.getId(),
             meterReading.getMinReading(),
             meterReading.getMaxReading(),
             meterReading.getConsumption(),
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
