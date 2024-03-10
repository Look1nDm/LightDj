package com.example.lightdj.web.mappers;

import com.example.lightdj.domain.operator.Operator;
import com.example.lightdj.web.dto.OperatorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperatorMapper {
    Operator toEntity(OperatorDto dto);

    OperatorDto toDto(Operator operator);

    List<OperatorDto> toDto(List<Operator> operators);
}
