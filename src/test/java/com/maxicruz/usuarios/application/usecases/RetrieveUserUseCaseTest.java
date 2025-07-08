package com.maxicruz.usuarios.application.usecases;

import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RetrieveUserUseCaseTest {

    private IUserRepositoryPort userRepository;
    private RetrieveUserUseCase useCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepositoryPort.class);
        useCase = new RetrieveUserUseCase(userRepository);
    }

    @Test
    void getAllUsers_returnsListFromRepository() {
        List<User> users = listOf(User.builder().build(), User.builder().build());
        when(userRepository.getAllUsers()).thenReturn(users);

        List<User> result = useCase.getAllUsers();

        assertEquals(users, result);
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void getUserById_returnsOptionalFromRepository() {
        UUID id = UUID.randomUUID();
        User user = User.builder().build();
        when(userRepository.getUserById(id)).thenReturn(Optional.of(user));

        Optional<User> result = useCase.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).getUserById(id);
    }

    @Test
    void getUserById_returnsEmptyWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.getUserById(id)).thenReturn(Optional.empty());

        Optional<User> result = useCase.getUserById(id);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).getUserById(id);
    }

    @Test
    void getUserByUsername_returnsOptionalFromRepository() {
        String username = "testuser";
        User user = User.builder().build();
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = useCase.getUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).getUserByUsername(username);
    }

    @Test
    void getUserByUsername_returnsEmptyWhenNotFound() {
        String username = "nouser";
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.empty());

        Optional<User> result = useCase.getUserByUsername(username);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).getUserByUsername(username);
    }
}
