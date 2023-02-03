package task7.service;

import org.springframework.stereotype.Service;
import task7.dao.jdbcTemplate.MeterDaoJdbcTemplate;
import task7.dto.MeterDto;
import task7.dto.mapper.DtoMapper;
import task7.model.Meter;

import java.util.List;

@Service
public class MeterService {

    private MeterDaoJdbcTemplate meterDaoJdbcTemplate;
    private DtoMapper dtoMapper;

    public MeterService(MeterDaoJdbcTemplate meterDaoJdbcTemplate,
                        DtoMapper dtoMapper) {
        this.meterDaoJdbcTemplate = meterDaoJdbcTemplate;
        this.dtoMapper = dtoMapper;
    }

    public MeterDto saveMeter(Meter meter) {
        return dtoMapper.toDTO(meterDaoJdbcTemplate.save(meter));
    }

    public MeterDto findById(Long id) {
        return dtoMapper.toDTO(meterDaoJdbcTemplate.getById(id));
    }

    public List<MeterDto> findAll() {
        return meterDaoJdbcTemplate.getAll().stream()
             .map(m -> dtoMapper.toDTO(m)).toList();
    }

    public MeterDto update(Meter meter) {
        return dtoMapper.toDTO(meterDaoJdbcTemplate.update(meter));
    }

    public void deleteById(Long id) {
        meterDaoJdbcTemplate.deleteById(id);
    }

    public void deleteAll() {
        meterDaoJdbcTemplate.deleteAll();
    }
}
