package task7.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import task7.dto.MeterReadingDto;
import task7.dto.PostDataJson;
import task7.dto.UsernamePasswordDto;
import task7.dto.mapper.DtoMapper;
import task7.dto.report.GroupReport;
import task7.jwt.JwtProvider;
import task7.model.Role;
import task7.model.User;
import task7.service.GroupReportService;
import task7.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@AllArgsConstructor
public class ApiController {

    private final GroupReportService groupReportService;
    private final DtoMapper dtoMapper;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register(@Valid @RequestBody UsernamePasswordDto request) {
        User user = userService.save(request.getUsername(), request.getPassword(), List.of(Role.METER));
        return jwtProvider.generateToken(user.getUsername());
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String auth(@RequestBody UsernamePasswordDto request) {
        User user = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        return jwtProvider.generateToken(user.getUsername());
    }

    @PostMapping(value = "/readings", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MeterReadingDto postReading(@Valid @RequestBody PostDataJson reportJson) throws Exception {
        return dtoMapper.toDTO(groupReportService.postReadingFromJson(reportJson));
    }

    @PostMapping(value = "/readings/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveExcel(@RequestBody MultipartFile file) throws IOException {
        groupReportService.saveExcelAsMeterReading(file);
        return "redirect:report";
    }

    @GetMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupReport> getPostDataJson() {
        return groupReportService.getGroupReports();
    }
}
