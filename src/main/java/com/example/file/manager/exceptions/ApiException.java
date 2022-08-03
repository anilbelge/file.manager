package com.example.file.manager.exceptions;

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 3072422838825109132L;
    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
