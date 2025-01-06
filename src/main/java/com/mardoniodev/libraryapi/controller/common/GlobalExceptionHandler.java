package com.mardoniodev.libraryapi.controller.common;

import com.mardoniodev.libraryapi.controller.dto.FieldErrorDetail;
import com.mardoniodev.libraryapi.controller.dto.ResponseError;
import com.mardoniodev.libraryapi.exceptions.DuplicateRegisterException;
import com.mardoniodev.libraryapi.exceptions.InvalidFieldException;
import com.mardoniodev.libraryapi.exceptions.OperationNotAllowedException;
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
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldError = e.getFieldErrors();
        List<FieldErrorDetail> errorsList = fieldError
                .stream()
                .map(fe -> new FieldErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Failed", errorsList);
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDublicateRegisterExcelption(DuplicateRegisterException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotAllowedException(OperationNotAllowedException e) {
        return ResponseError.defaultError(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidFieldException(InvalidFieldException e) {
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error",
                List.of(new FieldErrorDetail(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleUnhandledException(RuntimeException e) {
        return new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error has occurred. Please contact your system administrator.",
                List.of()
        );
    }

}
