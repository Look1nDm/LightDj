package com.example.lightdj.domain.exceptions;

public class NotFoundDraftApplicationsException extends RuntimeException{
    public NotFoundDraftApplicationsException(String message) {
        super(message);
    }
}
