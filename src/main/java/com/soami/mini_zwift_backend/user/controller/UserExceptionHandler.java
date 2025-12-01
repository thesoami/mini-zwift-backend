package com.soami.mini_zwift_backend.user.controller;

import com.soami.mini_zwift_backend.user.exceptions.InvalidUserDataException;
import com.soami.mini_zwift_backend.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 404,
                        "error", "NOT_FOUND",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUser(InvalidUserDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", 400,
                        "error", "BAD_REQUEST",
                        "message", ex.getMessage()
                ));
    }
}
