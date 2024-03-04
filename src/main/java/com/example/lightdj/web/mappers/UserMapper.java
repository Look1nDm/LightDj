package com.example.lightdj.web.mappers;

import com.example.lightdj.domain.user.User;
import com.example.lightdj.web.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);
}
