package com.example.lightdj.web.controller;

import com.example.lightdj.service.UserService;
import com.example.lightdj.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


}
