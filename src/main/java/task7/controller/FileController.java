package task7.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import task7.dto.report.GroupReport;
import task7.service.MainService;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class FileController {

    private final MainService mainService;

    @GetMapping("/report/download")
    public ResponseEntity<ByteArrayResource> reportExcel() throws IOException {
        List<GroupReport> reports = mainService.getGroupReports(mainService.getReadingReports());
        ByteArrayResource resource = mainService.groupRepostToExcel(reports);
        return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report.xls")
             .contentType(MediaType.APPLICATION_OCTET_STREAM)
             .contentLength(resource.contentLength())
             .body(resource);
    }
}
