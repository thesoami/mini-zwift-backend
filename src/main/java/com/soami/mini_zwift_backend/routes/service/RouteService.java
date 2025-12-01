package com.soami.mini_zwift_backend.routes.service;

import com.soami.mini_zwift_backend.routes.exceptions.InvalidRouteDataException;
import com.soami.mini_zwift_backend.routes.exceptions.RouteNotFoundException;
import com.soami.mini_zwift_backend.routes.dao.RouteDao;
import com.soami.mini_zwift_backend.routes.model.Route;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RouteService {
    private final RouteDao routeDao;

    public RouteService(RouteDao routeDao) { this.routeDao = routeDao; }

    public Route createRoute(String name, Double distance) {
        validateRouteName(name);
        validateRouteDistance(distance);

        return routeDao.create(name, distance);
    }

    public Route getRouteById(UUID id) {
        return routeDao.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Route not found for id: " + id));
    }

    public Route getRouteByName(String name) {
        return routeDao.findByName(name)
                .orElseThrow(() -> new RouteNotFoundException("Route not found for given name: " + name));
    }

    public List<Route> getAllRoutes() {
        return routeDao.findAll();
    }

    public void deleteRoute(UUID id) {
        routeDao.delete(id);
    }


    private void validateRouteName(String routeName) {
        if (routeName == null || routeName.isBlank()) {
            throw new InvalidRouteDataException("Route name must not be BLANK");
        }
        if (routeName.trim().length() > 255) {
            throw new InvalidRouteDataException("Route name is too long");
        }
    }
    private void validateRouteDistance(Double distanceKm) {
        if (distanceKm == null) {
            return; // distance is optional
        }
        if (distanceKm <= 0) {
            throw new InvalidRouteDataException("Route distance must be greater than zero");
        }
    }
}
