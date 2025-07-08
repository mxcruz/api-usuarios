package com.maxicruz.usuarios.infraestructure.adapters.in.controllers;

import com.maxicruz.usuarios.infraestructure.utils.SecurityContextTestUtils;
import com.maxicruz.usuarios.application.services.IUserService;
import com.maxicruz.usuarios.domain.models.User;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User Controller Test")
class UserControllerTest {

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextTestUtils.clearAuthentication();
    }

    @Test
    @DisplayName("when get all Users is empty")
    @WithMockUser(username = "testuser")
    void getAllUsersIsEmpty() throws Exception {
        // Arrange
        doReturn(
                emptyList()
        ).when(userService).getAllUsers();

        // Act
        var result = mockMvc.perform(get("/api/v1/users"));

        // Assert
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("when get all Users is not empty")
    @WithMockUser(username = "testuser")
    void getAllUsersIsNotEmpty() throws Exception {
        // Arrange
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .username("UserTest")
                .createdOn(LocalDateTime.now())
                .build();

        doReturn(
                Collections.singletonList(user1)).when(userService).getAllUsers();

        // Act
        var result = mockMvc.perform(get("/api/v1/users"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(jsonPath("$").isNotEmpty());
        result.andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("when get User is ok")
    @WithMockUser(username = "testuser")
    void getUserIsOk() throws Exception {
        // Arrange
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("UserTest")
                .createdOn(LocalDateTime.now())
                .build();

        doReturn(
                Optional.of(user)).when(userService).getUserById(user.getId());

        // Act
        var result = mockMvc.perform(get("/api/v1/users/" + user.getId()));

        // Assert
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").isNotEmpty());
        result.andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    @DisplayName("when get User is not found")
    @WithMockUser(username = "testuser")
    void getUserIsNotFound() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        doReturn(Optional.empty()).when(userService).getUserById(userId);

        // Act
        var result = mockMvc.perform(get("/api/v1/users/" + userId));

        // Assert
        result.andExpect(status().isNotFound());
    }
}