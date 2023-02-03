package task7.dao.jdbcTemplate;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import task7.dao.mapper.MeterGroupMapper;
import task7.model.Meter;
import task7.model.MeterGroup;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
public class MeterGroupDaoJdbcTemplate {

    private JdbcTemplate jdbcTemplate;

    public MeterGroupDaoJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MeterGroup save(MeterGroup meterGroup) {
        String query = "insert into meter_group(id, name) values (?, ?)";
        Object[] args = new Object[]{ meterGroup.getId(), meterGroup.getName()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meterGroup;
        }
        return null;
    }

    public List<MeterGroup> getAll() {
        String query = "select id, name from meter_group";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(query);

        return list.stream().map(map -> {
            MeterGroup meterGroup = new MeterGroup();
            meterGroup.setId(Long.parseLong(String.valueOf(map.get("id"))));
            meterGroup.setName(String.valueOf(map.get("name")));
            return meterGroup;
        }).toList();
    }

    public MeterGroup getById(Long id) {
        String query = "select id, name from meter_group where id=?";
        MeterGroup meterGroup = jdbcTemplate.queryForObject(query, new MeterGroupMapper(), id);
        return meterGroup;
    }

    public MeterGroup update(MeterGroup meterGroup) {
        String query = "update meter_group set id=? ,name=?";
        Object[] args = new Object[] {meterGroup.getId(), meterGroup.getName()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meterGroup;
        }
        return null;
    }

    public void deleteById(Long id) {
        String query = "delete from meter_group where id=?";
        jdbcTemplate.update(query, id);
    }

    public void deleteAll() {
        String query = "delete from meter_group";
        jdbcTemplate.update(query);
    }
}
