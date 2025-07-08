package com.maxicruz.usuarios.application.usecases;

import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateUserUseCaseTest {

    private IUserRepositoryPort userRepository;
    private UpdateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepositoryPort.class);
        useCase = new UpdateUserUseCase(userRepository);
    }

    @Test
    void updateUser_delegatesToRepositoryAndReturnsResult() {
        User user = User.builder().build();
        when(userRepository.updateUser(user)).thenReturn(user);

        User result = useCase.updateUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).updateUser(user);
    }
}
