package com.soami.mini_zwift_backend.user;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return User
                .builder()
                .id((UUID) resultSet.getObject("id"))
                .email(resultSet.getString("email"))
                .displayName(resultSet.getString("display_name"))
                .createdAt(resultSet.getTimestamp("created_at").toInstant())
                .build();
    }
}
