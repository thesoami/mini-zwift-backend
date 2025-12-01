package com.example.zwift.user;

import com.soami.mini_zwift_backend.user.model.User;
import com.soami.mini_zwift_backend.user.dao.UserDao;
import com.soami.mini_zwift_backend.user.UserService;
import com.soami.mini_zwift_backend.user.exceptions.InvalidUserDataException;
import com.soami.mini_zwift_backend.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final UserService userService = new UserService(userDao);

    @Test
    void createUser_validData_callsDaoAndReturnsUser() {
        String email = "valid@example.com";
        String displayName = "Valid User";

        User fake = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .displayName(displayName)
                .createdAt(Instant.now())
                .build();

        when(userDao.create(email, displayName)).thenReturn(fake);

        User result = userService.createUser(email, displayName);

        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getDisplayName()).isEqualTo(displayName);
        verify(userDao).create(email, displayName);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void createUser_blankEmail_throwsInvalidUserDataException_andDoesNotCallDao() {
        assertThatThrownBy(() -> userService.createUser("   ", "Some Name"))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Email must not be BLANK");

        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_invalidEmail_throwsInvalidUserDataException_andDoesNotCallDao() {
        assertThatThrownBy(() -> userService.createUser("not-an-email", "Some Name"))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Email is not valid");

        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_blankDisplayName_throwsInvalidUserDataException_andDoesNotCallDao() {
        assertThatThrownBy(() -> userService.createUser("user@example.com", "   "))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Display name must not be BLANK");

        verifyNoInteractions(userDao);
    }

    @Test
    void getUserById_found_returnsUser() {
        UUID id = UUID.randomUUID();
        User fake = User.builder()
                .id(id)
                .email("test@example.com")
                .displayName("Test User")
                .createdAt(Instant.now())
                .build();

        when(userDao.findById(id)).thenReturn(Optional.of(fake));

        User result = userService.getUserById(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(userDao).findById(id);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void getUserById_notFound_throwsUserNotFoundException() {
        UUID id = UUID.randomUUID();
        when(userDao.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userDao).findById(id);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void getAllUsers_delegatesToDao() {
        when(userDao.findAll()).thenReturn(List.of(
                User.builder().id(UUID.randomUUID()).email("a@example.com").displayName("A").createdAt(Instant.now()).build(),
                User.builder().id(UUID.randomUUID()).email("b@example.com").displayName("B").createdAt(Instant.now()).build()
        ));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(2);
        verify(userDao).findAll();
        verifyNoMoreInteractions(userDao);
    }
}
