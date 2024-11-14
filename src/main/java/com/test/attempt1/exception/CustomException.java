package com.test.attempt1.exception;

/**
 * Custom exception: todo
 */
public final class CustomException extends RuntimeException {
    public CustomException() {
        super();
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}