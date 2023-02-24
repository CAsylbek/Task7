package task7.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task7.dto.MeterDto;
import task7.dto.MeterGroupDto;
import task7.dto.MeterReadingDto;
import task7.dto.mapper.DtoMapper;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;
import task7.service.MeterGroupService;
import task7.service.MeterReadingService;
import task7.service.MeterService;

import java.util.List;

@RestController
@AllArgsConstructor
public class ApiCrudController {


    private final MeterGroupService meterGroupService;
    private final MeterService meterService;
    private final MeterReadingService meterReadingService;
    private final DtoMapper dtoMapper;

    @PostMapping("/group")
    public MeterGroupDto groupSave(@RequestBody MeterGroupDto groupDto) {
        MeterGroup group = dtoMapper.toEntity(groupDto);
        return dtoMapper.toDTO(meterGroupService.save(group));
    }

    @GetMapping("/group/{id}")
    public MeterGroupDto groupGet(@PathVariable Long id) {
        return dtoMapper.toDTO(meterGroupService.findById(id));
    }

    @GetMapping("/group")
    public List<MeterGroupDto> groupGet() {
        return meterGroupService.findAll().stream().map(dtoMapper::toDTO).toList();
    }

    @DeleteMapping("/group/{id}")
    public void groupDelete(@PathVariable Long id) {
        meterGroupService.deleteById(id);
    }

    @PostMapping("/meter")
    public MeterDto meterSave(@RequestBody MeterDto meterDto) {
        MeterGroup group = meterGroupService.findById(meterDto.getMeterGroup().getId());
        if (group == null) {
            throw new EntityNotFoundException("Meter group mot found");
        }
        Meter meter = dtoMapper.toEntity(meterDto);
        meter.setMeterGroup(group);
        return dtoMapper.toDTO(meterService.save(meter));
    }

    @GetMapping("/meter/{id}")
    public MeterDto meterGet(@PathVariable Long id) {
        return dtoMapper.toDTO(meterService.findById(id));
    }

    @GetMapping("/meter")
    public List<MeterDto> meterGet() {
        return meterService.findAll().stream().map(dtoMapper::toDTO).toList();
    }

    @DeleteMapping("/meter/{id}")
    public void meterDelete(@PathVariable Long id) {
        meterService.deleteById(id);
    }

    @PostMapping("/reading")
    public MeterReadingDto readingSave(@RequestBody MeterReadingDto readingDto) {
        Meter meter = meterService.findById(readingDto.getMeter().getId());
        if (meter == null) {
            throw new EntityNotFoundException("Meter mot found");
        }
        MeterReading reading = dtoMapper.toEntity(readingDto);
        reading.setMeter(meter);
        return dtoMapper.toDTO(meterReadingService.save(reading));
    }

    @GetMapping("/reading/{id}")
    public MeterReadingDto readingGet(@PathVariable Long id) {
        return dtoMapper.toDTO(meterReadingService.findById(id));
    }

    @GetMapping("/reading")
    public List<MeterReadingDto> readingGet() {
        return meterReadingService.findAll().stream().map(dtoMapper::toDTO).toList();
    }

    @DeleteMapping("/reading/{id}")
    public void readingDelete(@PathVariable Long id) {
        meterReadingService.deleteById(id);
    }
}
