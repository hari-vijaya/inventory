package com.codebasicz.inventory.exception.custom;

import org.springframework.http.HttpStatus;

public class FailedToConvertFileToObject extends RuntimeException {
    private final HttpStatus status = HttpStatus.CONFLICT;
    private String message;

    public FailedToConvertFileToObject(String message) {
        super(message);
        this.message = message;
    }
}
