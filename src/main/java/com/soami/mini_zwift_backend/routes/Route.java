package com.soami.mini_zwift_backend.routes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private UUID id;
    private String name;
    private Double distanceKm;
    private Instant createdAt;
}
