package com.example.lightdj.service.impl;

import com.example.lightdj.domain.Role;
import com.example.lightdj.domain.exceptions.UserNotFoundException;
import com.example.lightdj.domain.operator.Operator;
import com.example.lightdj.domain.user.SimpleUser;
import com.example.lightdj.repository.SimpleUserRepository;
import com.example.lightdj.service.OperatorService;
import com.example.lightdj.service.SimpleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleUserServiceImpl implements SimpleUserService {

    private final SimpleUserRepository simpleUserRepository;
    private final OperatorService operatorService;

    @Override
    public SimpleUser findUserById(Long userId) {
        return simpleUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    @Override
    public SimpleUser update(SimpleUser simpleUser) {
        return simpleUserRepository.save(simpleUser);
    }

    @Override
    public SimpleUser getByEmail(String email) {
        return simpleUserRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }


    @Override
    public SimpleUser findByUsername(String username) {
        return simpleUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким именем не найден"));
    }

    @Override
    public List<SimpleUser> getAllUsers() {
        return simpleUserRepository.findAll();
    }

    @Override
    public Operator setStatus(Long id) {
        SimpleUser simpleUser = findUserById(id);
        Operator newOperator = new Operator();
        newOperator.setId(simpleUser.getId());
        newOperator.setEmail(simpleUser.getEmail());
        newOperator.setPassword(simpleUser.getPassword());
        newOperator.setPasswordConfirmation(simpleUser.getPasswordConfirmation());
        newOperator.setRoles(simpleUser.getRoles());
        newOperator.getRoles().clear();
        newOperator.getRoles().add(Role.OPERATOR);
        newOperator.setUsername(simpleUser.getUsername());
        simpleUserRepository.deleteById(simpleUser.getId());
        return operatorService.createOperator(newOperator);
    }
}
