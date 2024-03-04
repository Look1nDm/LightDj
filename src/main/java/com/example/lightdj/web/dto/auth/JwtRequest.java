package com.example.lightdj.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

    @Schema(description = "Phone number", example = "usermail2.com")
    @NotNull(message = "Username most be not null.")
    private String email;

    @Schema(description = "Password", example = "12345")
    @NotNull(message = "Password most be not null.")
    private String password;
}
