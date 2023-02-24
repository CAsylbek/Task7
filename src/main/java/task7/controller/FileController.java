package task7.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
        ByteArrayResource resource = mainService.groupRepostToExcelFile(reports);
        return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report.xls")
             .contentType(MediaType.APPLICATION_OCTET_STREAM)
             .contentLength(resource.contentLength())
             .body(resource);
    }

    @PostMapping(value = "/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveExcel(@RequestParam MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        mainService.excelFileToGroupReport(new ByteArrayResource(bytes));
    }
}
