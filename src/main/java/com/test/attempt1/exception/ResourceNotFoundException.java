package com.test.attempt1.exception;

/**
 * Custom ResourceNotFound exception: todo
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
