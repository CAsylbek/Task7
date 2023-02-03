package task7.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import task7.dto.MeterDto;
import task7.dto.MeterGroupDto;
import task7.dto.MeterReadingDto;
import task7.model.Meter;
import task7.model.MeterGroup;
import task7.model.MeterReading;

@Component
public class DtoMapper {

    private ModelMapper modelMapper;

    public DtoMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Meter.class, MeterDto.class);
        modelMapper.createTypeMap(MeterDto.class, Meter.class);
        modelMapper.createTypeMap(MeterGroup.class, MeterGroupDto.class);
        modelMapper.createTypeMap(MeterGroupDto.class, MeterGroup.class);
        modelMapper.createTypeMap(MeterReading.class, MeterReadingDto.class);
        modelMapper.createTypeMap(MeterReadingDto.class, MeterReading.class);
    }

    public MeterDto toDTO(Meter domainModel) {
        return modelMapper.map(domainModel, MeterDto.class);
    }

    public MeterGroupDto toDTO(MeterGroup domainModel) {
        return modelMapper.map(domainModel, MeterGroupDto.class);
    }

    public MeterReadingDto toDTO(MeterReading domainModel) {
        return modelMapper.map(domainModel, MeterReadingDto.class);
    }

    public Meter toEntity(MeterDto dto) {
        return modelMapper.map(dto, Meter.class);
    }

    public MeterGroup toEntity(MeterGroupDto dto) {
        return modelMapper.map(dto, MeterGroup.class);
    }

    public MeterReading toEntity(MeterReadingDto dto) {
        return modelMapper.map(dto, MeterReading.class);
    }
}
