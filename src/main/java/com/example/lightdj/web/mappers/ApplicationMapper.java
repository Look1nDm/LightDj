package com.example.lightdj.web.mappers;

import com.example.lightdj.domain.application.Application;
import com.example.lightdj.web.dto.ApplicationDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    Application toEntity(ApplicationDto applicationDto);

    ApplicationDto toDto(Application application);

    List<ApplicationDto> toDto(List<Application> applications);
}
