package com.example.lightdj.web.controller;

import com.example.lightdj.domain.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdviceController {

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BodyException handleResourceMapping(final ApplicationNotFoundException e) {
        return new BodyException(e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BodyException handleStatusMapping(final IllegalArgumentStatusException e){
        return new BodyException(e.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BodyException handleResourceUserMapping(final UserNotFoundException e) {
        return new BodyException(e.getMessage());
    }
    @ExceptionHandler(NotFoundDraftApplicationsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BodyException handleResourceDraftApplication(final NotFoundDraftApplicationsException e) {
        return new BodyException(e.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleIllegalState(final IllegalStateException e) {
        return new BodyException(e.getMessage());
    }
    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BodyException handleAccessDenied() {
        return new BodyException("Access denied");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        BodyException bodyException = new BodyException("Valid failed");
        List<FieldError> errorList = e.getBindingResult().getFieldErrors();
        bodyException.setErrors(errorList.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return bodyException;
    }
    @ExceptionHandler(DontSuchSortedMethodException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleFinedSortedArgument(final DontSuchSortedMethodException e) {
        return new BodyException(e.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleConstraintViolation(final ConstraintViolationException e) {
        BodyException bodyException = new BodyException("Validation failed");
        bodyException.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString()
                        , ConstraintViolation::getMessage)));
        return bodyException;
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleAuthentication(final AuthenticationException e) {
        return new BodyException("Authentication failed");
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BodyException handleException(Exception e) {
        e.printStackTrace();
        return new BodyException("Internal error");
    }
}
