package com.example.lightdj.web.controller;

import com.example.lightdj.service.AuthService;
import com.example.lightdj.web.dto.auth.JwtRequest;
import com.example.lightdj.web.dto.auth.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Authentication API")
public class AuthController {

    private final AuthService authService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();

    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/login")
    @Operation(summary = "Authorization user")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        logger.info("Вход в ЛК (контроллер)");
        return authService.login(loginRequest);
    }
    @PostMapping("/logout")
    public void logout(Authentication authentication,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        if (authentication != null){
            request.getSession().invalidate();
            securityContextLogoutHandler.logout(request, response, authentication);
        }
    }
}
