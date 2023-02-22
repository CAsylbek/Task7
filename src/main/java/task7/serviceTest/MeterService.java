package task7.serviceTest;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task7.model.Meter;
import task7.repository.MeterRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MeterService {

    private final MeterRepository meterRepository;

    public Meter save(Meter meter) {
        return meterRepository.save(meter);
    }

    public Meter findById(Long id) {
        return meterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Meter> findAll() {
        return meterRepository.findAll();
    }

    public void deleteById(Long id) {
        meterRepository.deleteById(id);
    }

    public void deleteAll() {
        meterRepository.deleteAll();
    }
}
