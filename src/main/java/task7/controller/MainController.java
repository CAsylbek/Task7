package task7.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task7.service.MainService;

@Controller
@AllArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/report")
    public String getReport(Model model) {
        model.addAttribute("readings", mainService.getGroupReports(mainService.getReadingReports()));
        return "report";
    }
}
