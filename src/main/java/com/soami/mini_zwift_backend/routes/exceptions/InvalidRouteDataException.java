package com.soami.mini_zwift_backend.routes.exceptions;

public class InvalidRouteDataException extends RuntimeException {
    public InvalidRouteDataException(String message) {
        super(message);
    }
}
