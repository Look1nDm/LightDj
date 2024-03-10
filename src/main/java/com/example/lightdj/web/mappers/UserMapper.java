package com.example.lightdj.web.mappers;

import com.example.lightdj.domain.user.SimpleUser;
import com.example.lightdj.web.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    SimpleUser toEntity(UserDto userDto);

    UserDto toDto(SimpleUser simpleUser);

    List<UserDto> toDto(List<SimpleUser> simpleUsers);
}
