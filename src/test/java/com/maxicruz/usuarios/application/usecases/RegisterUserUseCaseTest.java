package com.maxicruz.usuarios.application.usecases;

import com.maxicruz.usuarios.domain.models.Role;
import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    private IUserRepositoryPort userRepository;
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepositoryPort.class);
        registerUserUseCase = new RegisterUserUseCase(userRepository);
    }

    @Test
    void registerUser_shouldCreateUserWithCorrectFields() {
        // Arrange
        String username = "testuser";
        String password = "testpass";
        String email = "test@example.com";

        User expectedUser = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .createdOn(LocalDateTime.now())
                .role(Role.USER)
                .build();
        when(userRepository.createUser(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = registerUserUseCase.registerUser(username, password, email);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(email, result.getEmail());
        assertEquals(Role.USER, result.getRole());
        assertNotNull(result.getCreatedOn());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).createUser(userCaptor.capture());
        User captured = userCaptor.getValue();
        assertEquals(username, captured.getUsername());
        assertEquals(password, captured.getPassword());
        assertEquals(email, captured.getEmail());
        assertEquals(Role.USER, captured.getRole());
        assertNotNull(captured.getCreatedOn());
    }
}
