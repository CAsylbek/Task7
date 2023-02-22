package task7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task7.model.Meter;

public interface MeterRepository extends JpaRepository<Meter, Long> {
}