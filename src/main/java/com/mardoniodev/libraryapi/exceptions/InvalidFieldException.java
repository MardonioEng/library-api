package com.mardoniodev.libraryapi.exceptions;

public class InvalidFieldException extends RuntimeException {

    private String field;

    public InvalidFieldException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
