package com.example.lightdj.service;

import com.example.lightdj.domain.user.User;

public interface UserService {
    User findUserById(Long userId);

    User update(User user);

    User getUserByEmail(String email);

    User findOperator();
    User findByUsername(String username);
}
