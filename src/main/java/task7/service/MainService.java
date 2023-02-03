package task7.service;

import org.springframework.stereotype.Service;

@Service
public class MainService {

    private MeterService meterService;
    private MeterGroupService meterGroupService;
    private MeterReadingService meterReadingService;

    public MainService(MeterService meterService,
                       MeterGroupService meterGroupService,
                       MeterReadingService meterReadingService) {
        this.meterService = meterService;
        this.meterGroupService = meterGroupService;
        this.meterReadingService = meterReadingService;
    }


}
