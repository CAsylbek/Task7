package task7.dao.jdbcTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import task7.dao.mapper.MeterGroupExtractor;
import task7.model.MeterGroup;

import java.util.List;

@Component
public class MeterGroupDaoJdbcTemplate {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MeterGroupExtractor meterGroupExtractor;

    public MeterGroupDaoJdbcTemplate(JdbcTemplate jdbcTemplate,
                                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     MeterGroupExtractor meterGroupExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.meterGroupExtractor = meterGroupExtractor;
    }

    public MeterGroup save(MeterGroup meterGroup) {
        String query = "insert into meter_group(name) values (?)";
        Object[] args = new Object[]{meterGroup.getName()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meterGroup;
        }
        return null;
    }

    public List<MeterGroup> getAll() {
        String query = "select * from meter_group";
        return jdbcTemplate.query(query, meterGroupExtractor);
    }

    public MeterGroup getById(Long id) {
        String query = "select * from meter_group where id=?";
        return jdbcTemplate.query(query, meterGroupExtractor, id)
             .stream().findFirst().orElse(null);
    }

    public List<MeterGroup> getListByIds(List<Long> ids) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("ids", ids);
        String query = "select * from meter_group where id in(:ids)";
        return namedParameterJdbcTemplate.query(query, parameterSource, meterGroupExtractor);
    }

    public List<MeterGroup> getByName(String name) {
        String query = "select * from meter_group where name=?";
        return jdbcTemplate.query(query, meterGroupExtractor, name);
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
