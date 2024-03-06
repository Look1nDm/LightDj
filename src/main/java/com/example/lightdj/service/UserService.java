package com.example.lightdj.service;

import com.example.lightdj.domain.user.Role;
import com.example.lightdj.domain.user.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);

    User update(User user);

    User getUserByEmail(String email);

    User findOperator();
    User findByUsername(String username);
    List<User> getAllUsers();
    User setStatus(Long id);
}
