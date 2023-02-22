package task7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task7.model.MeterGroup;

import java.util.List;

public interface MeterGroupRepository extends JpaRepository<MeterGroup, Long> {

    List<MeterGroup> findByName(String name);
}
