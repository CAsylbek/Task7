package task7.serviceTest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task7.model.MeterGroup;
import task7.repository.MeterGroupRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MeterGroupService {

    private final MeterGroupRepository meterGroupRepository;

    public MeterGroup save(MeterGroup meterGroup) {
        return meterGroupRepository.save(meterGroup);
    }

    public MeterGroup findById(Long id) {
        return meterGroupRepository.findById(id).orElse(null);
    }

    public List<MeterGroup> findByName(String name) {
        return meterGroupRepository.findByName(name);
    }

    public List<MeterGroup> findAll() {
        return meterGroupRepository.findAll();
    }

    public void deleteById(Long id) {
        meterGroupRepository.deleteById(id);
    }


    public void deleteAll() {
        meterGroupRepository.deleteAll();
    }
}
