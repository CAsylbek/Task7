package task7.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import task7.service.MainService;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/report")
    public String getReport(Model model) {
        model.addAttribute("readings", mainService.getGroupReports(mainService.getReadingReports()));
        return "report";
    }

    @PostMapping(value = "/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveExcel(@RequestParam MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        mainService.saveExcelAsMeterReading(new ByteArrayResource(bytes));
        return "redirect:report";
    }
}
