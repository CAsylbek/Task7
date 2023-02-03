package task7.dao.jdbcTemplate;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import task7.dao.mapper.MeterMapper;
import task7.model.Meter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
public class MeterDaoJdbcTemplate {

    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper beanPropertyRowMapper;
    private MeterMapper meterMapper;

    public MeterDaoJdbcTemplate(JdbcTemplate jdbcTemplate,
                                MeterMapper meterMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.beanPropertyRowMapper = new BeanPropertyRowMapper(Meter.class);
        this.meterMapper = meterMapper;
    }


    public Meter save(Meter meter) {
        //todo: авто установка id
        String query = "insert into meter(id, name, meter_group) values (?, ?, ?)";
        Object[] args = new Object[]{
             meter.getId(),
             meter.getName(),
             meter.getMeterGroup().getId()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meter;
        }
        return null;
        //todo: вместо null можно вызывать исключение
    }

    public List<Meter> getAll() {
        String query = "select id, name, meter_group from meter";
        return jdbcTemplate.query(query, meterMapper);

//        List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
//
//        return list.stream().map(map -> {
//            Meter meter = new Meter();
//            meter.setId(Long.parseLong(String.valueOf(map.get("id"))));
//            meter.setName(String.valueOf(map.get("name")));
//            meter.setGroupId(Long.parseLong(String.valueOf(map.get("group_id"))));
//            return meter;
//        }).toList();
    }

    public Meter getById(Long id) {
        String query = "select id, name, meter_group from meter where id=?";
        Meter meter = (Meter)jdbcTemplate.queryForObject(query, meterMapper, id);
        return meter;
    }

    public Meter update(Meter meter) {
        String query = "update meter set id=? ,name=?, meter_group=?";
        Object[] args = new Object[]{
             meter.getId(),
             meter.getName(),
             meter.getMeterGroup().getId()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meter;
        }
        return null;
    }

    public void deleteById(Long id) {
        String query = "delete from meter where id=?";
        jdbcTemplate.update(query, id);
    }

    public void deleteAll() {
        String query = "delete from meter";
        jdbcTemplate.update(query);
    }
}
