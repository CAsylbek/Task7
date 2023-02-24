package task7.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task7.model.MeterReading;
import task7.repository.MeterReadingRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;

    public MeterReading save(MeterReading meterReading) {
        return meterReadingRepository.save(meterReading);
    }

    public MeterReading findById(Long id) {
        return meterReadingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<MeterReading> findMaxMinReadings() {
        return meterReadingRepository.findMaxMinReadings();
    }

    public List<MeterReading> findAll() {
        return meterReadingRepository.findAll();
    }

    public void deleteById(Long id) {
        meterReadingRepository.deleteById(id);
    }

    public void deleteAll() {
        meterReadingRepository.deleteAll();
    }
}
