package task7.dto.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import task7.dto.MeterDto;
import task7.dto.MeterGroupDto;
import task7.dto.MeterReadingDto;
import task7.dto.UserDto;
import task7.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    private final ModelMapper modelMapper;
    private final Converter<Set<Role>, Set<String>> roleToStringConverter = (src) -> src.getSource().stream().map(Role::name).collect(Collectors.toSet());
    private final Converter<Set<String>, Set<Role>> stringToRoleConverter = (src) -> src.getSource().stream().map(Role::valueOf).collect(Collectors.toSet());

    private final Map<Class, Class> domainDtoMap = Map.of(
         MeterGroup.class, MeterGroupDto.class,
         Meter.class, MeterDto.class,
         MeterReading.class, MeterReadingDto.class,
         User.class, UserDto.class);

    public DtoMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Meter.class, MeterDto.class);
        modelMapper.createTypeMap(MeterDto.class, Meter.class);
        modelMapper.createTypeMap(MeterGroup.class, MeterGroupDto.class);
        modelMapper.createTypeMap(MeterGroupDto.class, MeterGroup.class);
        modelMapper.createTypeMap(MeterReading.class, MeterReadingDto.class);
        modelMapper.createTypeMap(MeterReadingDto.class, MeterReading.class);
        modelMapper.createTypeMap(User.class, UserDto.class)
             .addMappings(m -> m.using(roleToStringConverter).map(User::getRoles, UserDto::setRoles));
        modelMapper.createTypeMap(UserDto.class, User.class)
             .addMappings(m -> m.using(stringToRoleConverter).map(UserDto::getRoles, User::setRoles));
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

    public UserDto toDto(User domainModel) {
        return modelMapper.map(domainModel, UserDto.class);
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

    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public List<UserDto> listToDTO(List<User> domainModels) {
        return domainModels.stream().map(this::toDto).toList();
    }
}
