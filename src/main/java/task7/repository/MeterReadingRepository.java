package task7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import task7.model.MeterReading;

import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

//    @Query(value = "select * from meter_reading where (current_reading, meter_id) in" +
//         "(select max(current_reading) as current_reading, meter_id " +
//             "from meter_reading group by meter_id " +
//         "union " +
//         "select min(current_reading), meter_id " +
//             "from meter_reading group by meter_id)", nativeQuery = true)
    @Query("SELECT mr FROM MeterReading mr WHERE (mr.meter, mr.currentReading) IN " +
             "(SELECT r.meter, MAX(r.currentReading) FROM MeterReading r GROUP BY r.meter " +
             "UNION " +
             "SELECT r.meter, MIN(r.currentReading) FROM MeterReading r GROUP BY r.meter) ")
    List<MeterReading> findMaxMinReadings();
}
