package task7.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import task7.service.GroupReportService;

import java.io.IOException;

@Controller
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final GroupReportService groupReportService;

    @GetMapping
    public String getReport(Model model) {
        model.addAttribute("readings", groupReportService.getGroupReports());
        return "report";
    }

    @GetMapping("/excel")
    public ResponseEntity<ByteArrayResource> reportExcel() throws IOException {
        ByteArrayResource resource = groupReportService.groupRepostToExcel();
        return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report.xls")
             .contentType(MediaType.APPLICATION_OCTET_STREAM)
             .contentLength(resource.contentLength())
             .body(resource);
    }
}
