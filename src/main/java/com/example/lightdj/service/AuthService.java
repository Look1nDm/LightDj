package com.example.lightdj.service;

import com.example.lightdj.web.dto.auth.JwtRequest;
import com.example.lightdj.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
}
