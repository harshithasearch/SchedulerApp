package com.example.scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Global exception handler for REST APIs.
 * Handles custom and generic exceptions in a consistent format.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom application-level exceptions.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        return buildErrorResponse("Bad Request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles input validation errors for @Valid annotated DTOs.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> validationErrors = ex.getBindingResult()
                                          .getFieldErrors()
                                          .stream()
                                          .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                          .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("error", "Validation Failed");
        response.put("timestamp", LocalDateTime.now());
        response.put("details", validationErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles malformed JSON input.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        return buildErrorResponse("Malformed JSON request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles uncaught generic exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        return buildErrorResponse("Internal Server Error", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Helper method to build consistent error responses.
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String errorTitle, Object details, HttpStatus status) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", errorTitle);
        error.put("timestamp", LocalDateTime.now());
        error.put("details", details);
        return new ResponseEntity<>(error, status);
    }
}
