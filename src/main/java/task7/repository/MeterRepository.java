package task7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task7.model.Meter;
import task7.model.MeterGroup;

import java.util.List;

public interface MeterRepository extends JpaRepository<Meter, Long> {

    List<Meter> findByType(String type);
}