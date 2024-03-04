package com.example.lightdj.service.impl;

import com.example.lightdj.domain.application.Status;
import com.example.lightdj.domain.exceptions.UserNotFoundException;
import com.example.lightdj.domain.user.Role;
import com.example.lightdj.domain.user.User;
import com.example.lightdj.repository.UserRepository;
import com.example.lightdj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOperator() {
        List<User> operators = userRepository.findByRoles(Role.OPERATOR);
        return findFreeOperator(operators);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    private static User findFreeOperator(List<User> users){
        return users.stream()
                .min(Comparator.comparing(u -> u.getApplications().size()))
                .orElseThrow();
    }
}
