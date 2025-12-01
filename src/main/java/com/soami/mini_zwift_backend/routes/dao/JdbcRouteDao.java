package com.soami.mini_zwift_backend.routes.dao;

import com.soami.mini_zwift_backend.routes.model.Route;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcRouteDao implements RouteDao {

    private final JdbcTemplate jdbcTemplate;
    private final RouteRowMapper rowMapper = new RouteRowMapper();

    public JdbcRouteDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Route create(String name, Double distance) {
        String sql = """
                INSERT INTO routes (name, distance_km)
                VALUES (?, ?)
                RETURNING id, name, distance_km, created_at
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, name, distance);
    }

    @Override
    public Optional<Route> findById(UUID id) {
        String sql = "SELECT id, name, distance_km, created_at FROM routes WHERE id = ?";
        try {
            Route route = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(route);

        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Route> findByName(String name) {
        String sql = "SELECT id, name, distance_km, created_at FROM routes WHERE name = ?";

        try {
            Route route = jdbcTemplate.queryForObject(sql, rowMapper, name);
            return Optional.ofNullable(route);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Route> findAll() {
        String sql = "SELECT id, name, distance_km, created_at FROM routes ORDER BY name ASC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int delete(UUID id) {
        String sql = "DELETE FROM routes WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
