package com.soami.mini_zwift_backend.routes.exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String message) {
        super(message);
    }
}
