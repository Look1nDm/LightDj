package com.example.lightdj.web.dto;

import com.example.lightdj.domain.application.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(description = "Application dto")
public class ApplicationDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Status status;
    @Schema(description = "Текст обращения не более 500 символов", example = "Не почищен снег у парадной")
    private String textApplication;
    @Schema(description = "Имя обращающегося", example = "Василий")
    private String username;
    @Schema(description = "Контактный номер для связи", example = "+79997849320")
    private String phoneNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dateCreatedApplication;
}
