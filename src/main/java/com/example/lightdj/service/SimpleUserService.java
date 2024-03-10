package com.example.lightdj.service;

import com.example.lightdj.domain.operator.Operator;
import com.example.lightdj.domain.user.SimpleUser;

import java.util.List;

public interface SimpleUserService {
    SimpleUser findUserById(Long userId);

    SimpleUser update(SimpleUser simpleUser);

    SimpleUser getByEmail(String email);
    SimpleUser findByUsername(String username);
    List<SimpleUser> getAllUsers();
    Operator setStatus(Long id);
}
