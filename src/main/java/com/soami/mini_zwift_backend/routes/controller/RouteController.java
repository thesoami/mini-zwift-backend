package com.soami.mini_zwift_backend.routes.controller;

import com.soami.mini_zwift_backend.routes.model.Route;
import com.soami.mini_zwift_backend.routes.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    // POST /api/routes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Route createRoute(@RequestBody CreateRouteRequest request) {
        return routeService.createRoute(
                request.getRouteName(),
                request.getDistanceKm()
        );
    }

    // GET /api/routes
    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    // GET /api/route/{id}
    @GetMapping("/{id}")
    public Route getRouterById(@PathVariable UUID id) {
        return routeService.getRouteById(id);
    }

    // DELETE /api/route/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable UUID id) {
        routeService.deleteRoute(id);
    }
}
