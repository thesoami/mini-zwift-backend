package com.soami.mini_zwift_backend.routes;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoutesRowMapper implements RowMapper<Routes> {

    @Override
    public Routes mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Routes
                .builder()
                .id((UUID) resultSet.getObject("id"))
                .name(resultSet.getString("name"))
                .distanceKm(resultSet.getDouble("distance_km"))
                .createdAt(resultSet.getTimestamp("created_at").toInstant())
                .build();
    }
}
