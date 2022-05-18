package com.sonle.azuresso.security.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public InvalidTokenException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
