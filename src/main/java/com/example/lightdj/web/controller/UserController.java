package com.example.lightdj.web.controller;

import com.example.lightdj.service.UserService;
import com.example.lightdj.web.dto.UserDto;
import com.example.lightdj.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(summary = "Показать всех пользоватлей",
            description = "Выводим всех пользователей со статусом USER")
    @GetMapping("/all")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userService.getAllUsers());
    }

    @Operation(summary = "Изменение статуса пользователю",
            description = "Меняем статус USER на OPERATOR")
    @PutMapping("/setRole/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public HttpStatus setRoleUser(@PathVariable Long id) {
        userService.setStatus(id);
        return HttpStatus.OK;
    }
}
