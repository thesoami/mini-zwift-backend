package com.soami.mini_zwift_backend.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.zwift.user.JdbcUserDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(com.example.zwift.user.JdbcUserDao.class)
public class JdbcUserDaoTest {

    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDb() {
        jdbcTemplate.update("DELETE FROM users");
    }

    @Test
    void create_and_findById_roundTrip_include_displayName() {
        // arrange object
        String email = "test-username@example.com";
        String displayName = "DAO Test User";

        // act
        User created = jdbcUserDao.create(email, displayName);
        Optional<User> found = jdbcUserDao.findById(created.getId());

        // assert
        assertThat(found).isPresent();

        User user = found.get();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getDisplayName()).isEqualTo(displayName);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getCreatedAt()).isNotNull();
    }

    @Test
    void findAll_returnsUsers_withDisplayNames() {
        jdbcUserDao.create("a@example.com", "User A");
        jdbcUserDao.create("b@example.com", "User B");

        List<User> users = jdbcUserDao.findAll();

        assertThat(users)
                .extracting(User::getEmail)
                .contains("a@example.com", "b@example.com");

        assertThat(users)
                .extracting(User::getDisplayName)
                .contains("User A", "User B");
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        Optional<User> result = jdbcUserDao.findById(UUID.randomUUID());
        assertThat(result).isEmpty();
    }

    @Test
    void delete_removesRow() {
        User created = jdbcUserDao.create("delete-me@example.com", "Delete Me");

        int rows = jdbcUserDao.delete(created.getId());
        Optional<User> afterDelete = jdbcUserDao.findById(created.getId());

        assertThat(rows).isEqualTo(1);
        assertThat(afterDelete).isEmpty();
    }
}
