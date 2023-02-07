package task7.service;

import org.springframework.stereotype.Service;
import task7.dao.jdbcTemplate.MeterReadingDaoJdbcTemplate;
import task7.dto.MeterReadingDto;
import task7.dto.mapper.DtoMapper;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;
import task7.dto.report.GroupReport;
import task7.dto.report.ReadingReport;

import java.util.ArrayList;
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

    public List<ReadingReport> getReadingReports() {
        List<MeterReading> readings = meterReadingDaoJdbcTemplate.getMaxMinReadings();
        List<ReadingReport> readingReports = new ArrayList<>();

        readings.stream().forEach(r -> {
            Meter meter = r.getMeter();
            ReadingReport readingReport = readingReports.stream().filter(rp -> rp.getMeter().equals(meter))
                 .findFirst().orElse(null);
            if (readingReport == null) {
                readingReport = new ReadingReport();
                readingReport.setMeter(meter);
                readingReport.setMaxReading(r.getCurrentReading());
                readingReports.add(readingReport);
            } else {
                int max = readingReport.getMaxReading();
                int reading = r.getCurrentReading();
                if (max < reading) {
                    readingReport.setMaxReading(reading);
                    readingReport.setMinReading(max);
                    readingReport.setConsumption(reading - max);
                } else {
                    readingReport.setMinReading(reading);
                    readingReport.setConsumption(max - reading);
                }
            }
        });

        return readingReports;
    }

    public List<GroupReport> getGroupReports(List<ReadingReport> readingReports) {
        List<GroupReport> groupReports = new ArrayList<>();

        readingReports.stream().forEach(r -> {
            MeterGroup group = r.getMeter().getMeterGroup();
            GroupReport groupReport = groupReports.stream().filter(gr -> gr.getMeterGroup().equals(group))
                 .findFirst().orElse(null);
            if (groupReport == null) {
                groupReport = new GroupReport();
                groupReport.setMeterGroup(group);
                groupReport.setReadings(List.of(r));
                groupReport.setConsumption(r.getConsumption());
                groupReports.add(groupReport);
            } else {
                List<ReadingReport> list = new ArrayList<>(groupReport.getReadings());
                list.add(r);
                groupReport.setReadings(list);
                groupReport.setConsumption(groupReport.getConsumption() + r.getConsumption());
            }
        });

        return groupReports;
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
