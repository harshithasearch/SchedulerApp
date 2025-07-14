package com.example.scheduler.exception;

/**
 * Custom exception used for application-specific runtime errors.
 * Can be thrown in services or controllers to indicate business logic violations.
 */
public class CustomException extends RuntimeException {

    /**
     * Creates a new CustomException with a specific message.
     *
     * @param message the detail message
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * Creates a new CustomException with a message and cause.
     *
     * @param message the detail message
     * @param cause   the original exception that caused this error
     */
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}