package com.maxicruz.usuarios.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Domain Test")
class UserTest {

    @Test
    @DisplayName("when User is build ok")
    void buidOKUserTest() {
        // Arrange

        // Act
        var userCreated = User.builder()
                .id(UUID.randomUUID())
                .username("UsuarioPrueba")
                .email("usuario@prueba.com")
                .createdOn(LocalDateTime.now())
                .build();

        // Assert
        assertNotNull(userCreated);
    }
}
