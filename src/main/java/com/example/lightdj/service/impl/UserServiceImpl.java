package com.example.lightdj.service.impl;

import com.example.lightdj.domain.User;
import com.example.lightdj.repository.UserRepository;
import com.example.lightdj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }
}
