package task7.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import task7.model.MeterGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MeterGroupExtractor implements ResultSetExtractor<List<MeterGroup>> {

    @Override
    public List<MeterGroup> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<MeterGroup> groups = new ArrayList<>();
        while (rs.next()) {
            MeterGroup group = new MeterGroup();
            group.setId(rs.getLong("id"));
            group.setName(rs.getString("name"));
            groups.add(group);
        }
        return groups;
    }
}
