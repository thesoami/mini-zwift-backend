package com.soami.mini_zwift_backend.routes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutesDao {
    Routes create(String name, Double distanceKm);

    Optional<Routes> findById(UUID id);

    Optional<Routes> findByName(String name);

    List<Routes> findAll();

    int delete(UUID id);
}
