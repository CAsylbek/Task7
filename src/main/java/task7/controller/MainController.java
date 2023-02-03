package task7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task7.dto.MeterDto;
import task7.dto.MeterGroupDto;
import task7.dto.MeterReadingDto;
import task7.service.MeterGroupService;
import task7.service.MeterReadingService;
import task7.service.MeterService;

import java.util.List;

@Controller
public class MainController {

    private MeterService meterService;
    private MeterGroupService meterGroupService;
    private MeterReadingService meterReadingService;

    public MainController(MeterService meterService,
                          MeterGroupService meterGroupService,
                          MeterReadingService meterReadingService) {
        this.meterService = meterService;
        this.meterGroupService = meterGroupService;
        this.meterReadingService = meterReadingService;
    }

    @GetMapping("/report")
    public String getReport(Model model) {
        List<MeterDto> meters = meterService.findAll();
        List<MeterGroupDto> meterGroups = meterGroupService.findAll();
        List<MeterReadingDto> meterReadings = meterReadingService.findAll();

        model.addAttribute("meters", meters);
        model.addAttribute("meterGroups", meterGroups);
        model.addAttribute("meterReadings", meterReadings);

        //todo: добавить html страницу
        return "report";
    }
}
