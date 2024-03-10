package com.example.lightdj.service;

import com.example.lightdj.domain.User;

public interface UserService {

    User getByEmail(String email);
}
