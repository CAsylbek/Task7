package task7.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import task7.dto.MeterReadingDto;
import task7.dto.PostDataJson;
import task7.service.MainService;

@RestController
public class ApiController {

    private MainService mainService;

    public ApiController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping(value = "api/v1.0/example", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MeterReadingDto postReading(@Valid @RequestBody PostDataJson reportJson) throws Exception {
        return mainService.postReadingFromJson(reportJson);
    }
}
