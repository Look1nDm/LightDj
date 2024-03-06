package com.example.lightdj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class LightDjApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightDjApplication.class, args);
    }

}
