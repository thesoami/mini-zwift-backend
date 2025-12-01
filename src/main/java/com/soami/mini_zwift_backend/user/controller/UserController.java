package com.example.zwift.user;

import com.soami.mini_zwift_backend.user.UserService;
import com.soami.mini_zwift_backend.user.dto.CreateUserRequest;
import com.soami.mini_zwift_backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(
                request.getEmail(),
                request.getDisplayName()
        );
    }

    // GET /api/users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
