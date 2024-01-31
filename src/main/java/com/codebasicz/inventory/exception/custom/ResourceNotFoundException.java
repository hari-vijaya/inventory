package com.codebasicz.inventory.exception.custom;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
