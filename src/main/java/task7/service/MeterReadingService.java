package task7.service;

import org.springframework.stereotype.Service;
import task7.dao.jdbcTemplate.MeterReadingDaoJdbcTemplate;
import task7.dto.MeterReadingDto;
import task7.dto.mapper.DtoMapper;
import task7.model.MeterReading;

import java.util.List;

@Service
public class MeterReadingService {

    private MeterReadingDaoJdbcTemplate meterReadingDaoJdbcTemplate;
    private DtoMapper dtoMapper;

    public MeterReadingService(MeterReadingDaoJdbcTemplate meterReadingDaoJdbcTemplate,
                               DtoMapper dtoMapper) {
        this.meterReadingDaoJdbcTemplate = meterReadingDaoJdbcTemplate;
        this.dtoMapper = dtoMapper;
    }

    public MeterReadingDto saveMeter(MeterReading meterReading) {
        return dtoMapper.toDTO(meterReadingDaoJdbcTemplate.save(meterReading));
    }

    public MeterReadingDto findById(Long id) {
        return dtoMapper.toDTO(meterReadingDaoJdbcTemplate.getById(id));
    }

    public List<MeterReadingDto> findAll() {
        return meterReadingDaoJdbcTemplate.getAll().stream()
             .map(m -> dtoMapper.toDTO(m)).toList();
    }

    public MeterReadingDto update(MeterReading meterReading) {
        return dtoMapper.toDTO(meterReadingDaoJdbcTemplate.update(meterReading));
    }

    public void deleteById(Long id) {
        meterReadingDaoJdbcTemplate.deleteById(id);
    }

    public void deleteAll() {
        meterReadingDaoJdbcTemplate.deleteAll();
    }
}
