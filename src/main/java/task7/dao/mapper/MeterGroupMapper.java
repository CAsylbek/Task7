package task7.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import task7.model.MeterGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeterGroupMapper implements RowMapper<MeterGroup> {

    @Override
    public MeterGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        MeterGroup meter = new MeterGroup();
        meter.setId(rs.getLong("id"));
        meter.setName(rs.getString("name"));
        return meter;
    }
}
