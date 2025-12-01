package com.soami.mini_zwift_backend.routes.controller;

import lombok.Data;

@Data
public class CreateRouteRequest {
    String routeName;
    Double distanceKm;
}
