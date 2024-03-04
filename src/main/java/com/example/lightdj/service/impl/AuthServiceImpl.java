package com.example.lightdj.service.impl;

import com.example.lightdj.domain.user.User;
import com.example.lightdj.service.AuthService;
import com.example.lightdj.service.UserService;
import com.example.lightdj.web.dto.auth.JwtRequest;
import com.example.lightdj.web.dto.auth.JwtResponse;
import com.example.lightdj.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        logger.info("Запуск процесса входа в личный кабинет(сервис)");
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        User user = userService.getUserByEmail(loginRequest.getEmail());
        jwtResponse.setId(user.getId());
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),
                user.getEmail(),
                user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),
                user.getEmail()));
        logger.info("Вход в ЛК завершен(сервис)");
        return jwtResponse;
    }
}
