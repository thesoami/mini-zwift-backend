package com.soami.mini_zwift_backend.routes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutesDao {
    Route create(String name, Double distanceKm);

    Optional<Route> findById(UUID id);

    Optional<Route> findByName(String name);

    List<Route> findAll();

    int delete(UUID id);
}
