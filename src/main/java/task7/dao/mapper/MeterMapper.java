package task7.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import task7.dao.jdbcTemplate.MeterGroupDaoJdbcTemplate;
import task7.model.Meter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MeterMapper implements RowMapper<Meter> {

    private MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate;

    public MeterMapper(MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate) {
        this.meterGroupDaoJdbcTemplate = meterGroupDaoJdbcTemplate;
    }
    @Override
    public Meter mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meter meter = new Meter();
        meter.setId(rs.getLong("id"));
        meter.setName(rs.getString("name"));
//        meter.setGroupId(rs.getLong("group_id"));
        Long id = rs.getLong("meter_group");
        meter.setMeterGroup(meterGroupDaoJdbcTemplate.getById(id));
        return meter;
    }
}
