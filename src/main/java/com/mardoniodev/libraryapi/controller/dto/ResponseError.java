package com.mardoniodev.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public record ResponseError(int status, String message, List<FieldError> fieldErrors) {

    public static ResponseError defaultError(String message) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseError conflict(String message) {
        return new ResponseError(HttpStatus.CONFLICT.value(), message, List.of());
    }

}