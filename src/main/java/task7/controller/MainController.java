package task7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task7.dto.report.GroupReport;
import task7.service.MeterReadingService;

import java.util.List;

@Controller
public class MainController {

    private MeterReadingService meterReadingService;

    public MainController(MeterReadingService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }

    @GetMapping("/report")
    public String getReport(Model model) {
        List<GroupReport> groupReports = meterReadingService.getGroupReports(meterReadingService.getReadingReports());
        int total = groupReports.stream().map(GroupReport::getConsumption).reduce(0, Integer::sum);
        model.addAttribute("readings", groupReports);
        model.addAttribute("total", total);
        return "report";
    }
}
