package com.soami.mini_zwift_backend.user;

import com.soami.mini_zwift_backend.user.dao.UserDao;
import com.soami.mini_zwift_backend.user.exceptions.InvalidUserDataException;
import com.soami.mini_zwift_backend.user.exceptions.UserNotFoundException;
import com.soami.mini_zwift_backend.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String email, String displayName) {
        validateEmail(email);
        validateDisplayName(displayName);

        return userDao.create(email, displayName);
    }

    public User getUserById(UUID id) {
        return userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    public User getUserByDisplayName(String displayName) {
        return userDao.findByDisplayName(displayName)
                .orElseThrow(() -> new UserNotFoundException("user not foud"));
    }

    public User getUserByEmail(String email) {
        validateEmail(email);
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with the email: " + email));
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(UUID id) {
        userDao.delete(id);
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidUserDataException("Email must not be BLANK");
        }

        String trimmed = email.trim();
        if (!trimmed.contains("@") || trimmed.length() > 255) {
            throw new InvalidUserDataException("Email is not valid");
        }
    }

    private void validateDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            throw new InvalidUserDataException("Display name must not be BLANK");
        }
        if (displayName.trim().length() > 255) {
            throw new InvalidUserDataException("Display name is too long");
        }
    }

}
