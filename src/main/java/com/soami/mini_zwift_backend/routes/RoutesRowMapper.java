package com.soami.mini_zwift_backend.routes;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoutesRowMapper implements RowMapper<Route> {

    @Override
    public Route mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Route
                .builder()
                .id((UUID) resultSet.getObject("id"))
                .name(resultSet.getString("name"))
                .distanceKm(resultSet.getObject("distance_km") != null
                        ? resultSet.getDouble("distance_km")
                        : null)
                .createdAt(resultSet.getTimestamp("created_at").toInstant())
                .build();
    }
}
