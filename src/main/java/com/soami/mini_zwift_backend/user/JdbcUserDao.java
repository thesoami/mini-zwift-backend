package com.example.zwift.user;

import com.soami.mini_zwift_backend.user.User;
import com.soami.mini_zwift_backend.user.UserDao;
import com.soami.mini_zwift_backend.user.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper rowMapper = new UserRowMapper();

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(String email, String displayName) {
        String sql = """
                INSERT INTO users (email, display_name)
                VALUES (?, ?)
                RETURNING id, email, display_name, created_at
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, email, displayName);
    }

    @Override
    public Optional<User> findById(UUID id) {
        String sql = "SELECT id, email, display_name, created_at FROM users WHERE id = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, display_name, created_at FROM users WHERE email = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper, email);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByDisplayName(String displayName) {
        String sql = "SELECT id, email, displayName, created_at, FROM users where displayName = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper, displayName);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, email, display_name, created_at FROM users ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int delete(UUID id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}