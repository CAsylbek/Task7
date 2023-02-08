package task7.service;

import org.springframework.stereotype.Service;
import task7.dao.jdbcTemplate.MeterGroupDaoJdbcTemplate;
import task7.dto.MeterGroupDto;
import task7.dto.mapper.DtoMapper;
import task7.model.MeterGroup;

import java.util.List;

@Service
public class MeterGroupService {

    private MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate;
    private DtoMapper dtoMapper;

    public MeterGroupService(MeterGroupDaoJdbcTemplate meterGroupDaoJdbcTemplate,
                             DtoMapper dtoMapper) {
        this.meterGroupDaoJdbcTemplate = meterGroupDaoJdbcTemplate;
        this.dtoMapper = dtoMapper;
    }

    public MeterGroupDto save(MeterGroup meterGroup) {
        return dtoMapper.toDTO(meterGroupDaoJdbcTemplate.save(meterGroup));
    }

    public MeterGroupDto save(MeterGroup meterGroup, Long id) {
        return dtoMapper.toDTO(meterGroupDaoJdbcTemplate.save(meterGroup, id));
    }

    public MeterGroupDto findById(Long id) {
        return dtoMapper.toDTO(meterGroupDaoJdbcTemplate.getById(id));
    }

    public List<MeterGroupDto> findAll() {
        return meterGroupDaoJdbcTemplate.getAll().stream()
             .map(m -> dtoMapper.toDTO(m)).toList();
    }

    public MeterGroupDto update(MeterGroup meterGroup) {
        return dtoMapper.toDTO(meterGroupDaoJdbcTemplate.update(meterGroup));
    }

    public void deleteById(Long id) {
        meterGroupDaoJdbcTemplate.deleteById(id);
    }

    public void deleteAll() {
        meterGroupDaoJdbcTemplate.deleteAll();
    }
}
