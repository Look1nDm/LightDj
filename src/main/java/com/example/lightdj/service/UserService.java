package com.example.lightdj.service;

import com.example.lightdj.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User findUserById(Long userId);

    User update(User user);

    User findOperator();

    User getUserByEmail(String email);
}
