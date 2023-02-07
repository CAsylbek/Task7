package task7.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import task7.dao.jdbcTemplate.MeterGroupDaoJdbcTemplate;
import task7.model.Meter;
import task7.model.MeterGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MeterExtractor implements ResultSetExtractor<List<Meter>> {

    private MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate;

    public MeterExtractor(MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate) {
        this.meterGroupDaoJdbcTemplate = meterGroupDaoJdbcTemplate;
    }

    @Override
    public List<Meter> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Meter> meters = new ArrayList<>();
        Map<Long, List<Meter>> idMetersMap = new HashMap<>();

        while (rs.next()) {
            Meter meter = new Meter();
            meter.setId(rs.getLong("id"));
            meter.setType(rs.getString("type"));
            Long groupId = rs.getLong("meter_group");
            idMetersMap.computeIfAbsent(groupId, m -> new ArrayList<>()).add(meter);
        }

        List<MeterGroup> groups = meterGroupDaoJdbcTemplate.getListByIds(List.copyOf(idMetersMap.keySet()));

        for (Long groupId : idMetersMap.keySet()) {
            MeterGroup group = groups.stream().filter(g -> g.getId().equals(groupId)).findFirst().orElse(null);
            for (Meter meter : idMetersMap.get(groupId)) {
                meter.setMeterGroup(group);
                meters.add(meter);
            }
        }

        return meters;
    }
}
