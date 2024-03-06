package com.example.lightdj.domain.exceptions;

import lombok.Data;

import java.util.Map;

@Data
public class BodyException {

    private String message;
    private Map<String, String> errors;

    public BodyException(final String message) {
        this.message = message;
    }
}
