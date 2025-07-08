package com.maxicruz.usuarios.application.services.impl;

import com.maxicruz.usuarios.application.services.ILoggingService;
import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.in.IRegisterUser;
import com.maxicruz.usuarios.domain.ports.in.IRetrieveUser;
import com.maxicruz.usuarios.domain.ports.in.IUpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private IRetrieveUser retrieveUser;
    private IRegisterUser registerUser;
    private IUpdateUser updateUser;
    private ILoggingService loggingService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        retrieveUser = mock(IRetrieveUser.class);
        registerUser = mock(IRegisterUser.class);
        updateUser = mock(IUpdateUser.class);
        loggingService = mock(ILoggingService.class);
        userService = new UserService(retrieveUser, registerUser, updateUser, loggingService);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = Collections.singletonList(mock(User.class));
        when(retrieveUser.getAllUsers()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(users, result);
        verify(loggingService).logDebug(anyString());
        verify(retrieveUser).getAllUsers();
    }

    @Test
    void testGetUserById() {
        // Arrange
        UUID id = UUID.randomUUID();
        User user = mock(User.class);
        Optional<User> optionalUser = Optional.of(user);
        when(retrieveUser.getUserById(id)).thenReturn(optionalUser);

        // Act
        Optional<User> result = userService.getUserById(id);

        // Assert
        assertEquals(optionalUser, result);
        verify(loggingService).logDebug(anyString(), eq(id));
        verify(retrieveUser).getUserById(id);
    }

    @Test
    void testGetUserByUsername() {
        // Arrange
        String username = "testuser";
        User user = mock(User.class);
        Optional<User> optionalUser = Optional.of(user);
        when(retrieveUser.getUserByUsername(username)).thenReturn(optionalUser);

        // Act
        Optional<User> result = userService.getUserByUsername(username);

        // Assert
        assertEquals(optionalUser, result);
        verify(loggingService).logDebug(anyString(), eq(username));
        verify(retrieveUser).getUserByUsername(username);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        String username = "testuser";
        String password = "password";
        String email = "test@example.com";
        User user = mock(User.class);
        when(registerUser.registerUser(username, password, email)).thenReturn(user);

        // Act
        User result = userService.registerUser(username, password, email);

        // Assert
        assertEquals(user, result);
        verify(loggingService).logDebug(anyString(), eq(username), eq(email));
        verify(registerUser).registerUser(username, password, email);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User user = mock(User.class);
        when(updateUser.updateUser(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertEquals(user, result);
        verify(loggingService).logDebug(anyString(), eq(user));
        verify(updateUser).updateUser(user);
    }
}
