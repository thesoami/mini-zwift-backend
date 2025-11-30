package com.soami.mini_zwift_backend.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    User create(String email, String displayName);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByDisplayName(String displayName);

    List<User> findAll();

    int delete(UUID id);
}
