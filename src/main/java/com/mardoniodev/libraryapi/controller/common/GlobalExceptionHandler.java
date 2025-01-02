package com.mardoniodev.libraryapi.controller.common;

import com.mardoniodev.libraryapi.controller.dto.FieldErrorDetail;
import com.mardoniodev.libraryapi.controller.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getFieldErrors();
        List<FieldErrorDetail> errorsList = fieldError
                .stream()
                .map(fe -> new FieldErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Failed", errorsList);
    }

}
