package com.soami.mini_zwift_backend.user;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
