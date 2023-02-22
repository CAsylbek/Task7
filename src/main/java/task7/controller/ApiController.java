package task7.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import task7.dto.MeterReadingDto;
import task7.dto.PostDataJson;
import task7.dto.mapper.DtoMapper;
import task7.dto.report.GroupReport;
import task7.serviceTest.MainService;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@AllArgsConstructor
public class ApiController {

    private final MainService mainService;
    private final DtoMapper dtoMapper;

    @PostMapping(value = "/example", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MeterReadingDto postReading(@Valid @RequestBody PostDataJson reportJson) throws Exception {
        return dtoMapper.toDTO(mainService.postReadingFromJson(reportJson));
    }

    @GetMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupReport> getPostDataJson() {
        return mainService.getGroupReports(mainService.getReadingReports());
    }
}
