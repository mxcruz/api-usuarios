package com.maxicruz.usuarios.infraestructure.adapters.out.persistence.repositories;

import com.maxicruz.usuarios.domain.models.Role;
import com.maxicruz.usuarios.infraestructure.adapters.out.persistence.entities.UserEntity;
import com.maxicruz.usuarios.domain.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("User Repository Adapter Test")
class UserRepositoryAdapterTest {

    @Autowired
    IUserRepository userRepository;

    @Test
    @DisplayName("when create User is ok")
    void createUserIsOk() {
        // Arrange
        var user = User.builder()
                .id(UUID.randomUUID())
                .username("UsuarioPrueba")
                .email("usuario@prueba.com")
                .createdOn(LocalDateTime.now())
                .role(Role.USER)
                .build();

        // Act
        var result = userRepository.save(UserEntity.of(user)).toUser();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("when get all Users is ok")
    void getAllUsersIsOk() {
        // Arrange
        userRepository.save(UserEntity.of(User.builder()
                        .username("UsuarioPrueba1")
                        .email("usuario1@prueba.com")
                        .createdOn(LocalDateTime.now())
                        .role(Role.USER)
                        .build()
                )).toUser();
        userRepository.save(UserEntity.of(User.builder()
                        .username("UsuarioPrueba2")
                        .email("usuario2@prueba.com")
                        .createdOn(LocalDateTime.now())
                        .role(Role.USER)
                        .build()
                )).toUser();
        userRepository.save(UserEntity.of(User.builder()
                        .username("UsuarioPrueba3")
                        .email("usuario3@prueba.com")
                        .createdOn(LocalDateTime.now())
                        .role(Role.USER)
                        .build()
                )).toUser();

        // Act
        var result = userRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("when get all Users is empty")
    void getAllUsersIsEmpty() {
        // Arrange

        // Act
        var result = userRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("when get User by Id is ok")
    void getUserByIdIsOk() {
        // Arrange
        var userSaved = userRepository.save(UserEntity.of(User.builder()
                .username("UsuarioPrueba")
                .email("usuario@prueba.com")
                .createdOn(LocalDateTime.now())
                .role(Role.USER)
                .build()
        )).toUser();

        // Act
        var result = userRepository.findById(userSaved.getId()).orElseThrow();

        // Assert
        assertNotNull(result);
        assertEquals( userSaved.getId(), result.getId());
    }

    @Test
    @DisplayName("when get User by Id is empty")
    void getUserByIdIsEmpty() {
        // Arrange

        // Act
        var result = userRepository.findById(UUID.randomUUID());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("when get User by username is ok")
    void getUserByUsernameIsOk() {
        // Arrange
        var userSaved = userRepository.save(
                UserEntity.of(User.builder()
                        .username("UsuarioPrueba")
                        .email("usuario@prueba.com")
                        .createdOn(LocalDateTime.now())
                        .role(Role.USER)
                        .build()
                )).toUser();
        // Act
        var result = userRepository.findByUsername(userSaved.getUsername()).orElseThrow();

        // Assert
        assertEquals( userSaved.getId(), result.getId());
        assertEquals( userSaved.getUsername(), result.getUsername());
    }
}