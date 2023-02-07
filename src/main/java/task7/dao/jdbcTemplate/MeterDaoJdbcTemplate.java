package task7.dao.jdbcTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import task7.dao.mapper.MeterExtractor;
import task7.model.Meter;

import java.util.List;

@Component
public class MeterDaoJdbcTemplate {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private MeterExtractor meterExtractor;

    public MeterDaoJdbcTemplate(JdbcTemplate jdbcTemplate,
                                NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                MeterExtractor meterExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedParameterJdbcTemplate;
        this.meterExtractor = meterExtractor;
    }


    public Meter save(Meter meter) {
        String query = "insert into meter(name, meter_group) values (?, ?)";
        Object[] args = new Object[]{
             meter.getType(),
             meter.getMeterGroup().getId()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            return meter;
        }
        return null;
    }

    public List<Meter> getAll() {
        String query = "select id, name, meter_group from meter";
        return jdbcTemplate.query(query, meterExtractor);
    }

    public Meter getById(Long id) {
        String query = "select * from meter where id=?";
        return jdbcTemplate.query(query, meterExtractor, id)
             .stream().findFirst().orElse(null);
    }

    public List<Meter> getListByIds(List<Long> ids) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("ids", ids);
        String query = "select * from meter where id in(:ids)";
        return namedJdbcTemplate.query(query, parameterSource, meterExtractor);
    }

    public Meter update(Meter meter) {
        String query = "update meter set id=? ,type=?, meter_group=?";
        Object[] args = new Object[]{
             meter.getId(),
             meter.getType(),
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
