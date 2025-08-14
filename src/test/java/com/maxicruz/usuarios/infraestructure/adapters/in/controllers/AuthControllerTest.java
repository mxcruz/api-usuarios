package com.maxicruz.usuarios.infraestructure.adapters.in.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxicruz.usuarios.application.services.IUserService;
import com.maxicruz.usuarios.domain.models.CustomUserDetails;
import com.maxicruz.usuarios.domain.models.Role;
import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.infraestructure.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Auth Controller Test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("login Should Return Access And Refresh Token")
    void loginShouldReturnAccessAndRefreshToken() throws Exception {
        // Arrange
        var username = "UsuarioPrueba";
        var password = "{noop}changeme";
        var passwordEncrypted = "password-encrypted";
        var accessToken = "mock-access-token";
        var refreshToken = "mock-refresh-token";

        var domainUser = User.builder()
                .username(username)
                .password(passwordEncrypted)
                .role(Role.USER)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(domainUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);
        doReturn(accessToken).when(jwtTokenProvider).generateAccessToken(domainUser);
        doReturn(refreshToken).when(jwtTokenProvider).generateRefreshToken(domainUser);

        // Act
        var result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", username, password))
        );

        // Assert
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").isNotEmpty());
        result.andExpect(jsonPath("$.accessToken").isNotEmpty());
        result.andExpect(jsonPath("$.accessToken").value(accessToken));
        result.andExpect(jsonPath("$.refreshToken").isNotEmpty());
        result.andExpect(jsonPath("$.refreshToken").value(refreshToken));
    }

    @Test
    @DisplayName("register Should Return Access And Refresh Token")
    void registerShouldReturnAccessAndRefreshToken() throws Exception {
        // Arrange
        var username = "UsuarioPrueba";
        var password = "{noop}changeme";
        var email = "usuario@prueba.com";
        var passwordEncrypted = "password-encrypted";
        var accessToken = "mock-access-token";
        var refreshToken = "mock-refresh-token";

        var userCreated = User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(passwordEncrypted)
                .email(email)
                .createdOn(LocalDateTime.now())
                .build();

        doReturn(userCreated).when(userService).registerUser(any(), any(), any());
        doReturn(passwordEncrypted).when(passwordEncoder).encode(any(String.class));
        doReturn(accessToken).when(jwtTokenProvider).generateAccessToken(any(User.class));
        doReturn(refreshToken).when(jwtTokenProvider).generateRefreshToken(any(User.class));

        // Act
        var result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\" }", username, password, email))
        );

        // Assert
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$").isNotEmpty());
        result.andExpect(jsonPath("$.accessToken").isNotEmpty());
        result.andExpect(jsonPath("$.accessToken").value(accessToken));
    }

    @Test
    @DisplayName("refresh Should Return New Access And Refresh Token")
    void refreshShouldReturnNewTokens() throws Exception {
        // Arrange
        var refreshToken = "valid-refresh-token";
        var newAccessToken = "new-access-token";
        var newRefreshToken = "new-refresh-token";
        var username = "UsuarioPrueba";
        var user = User.builder().username(username).build();

        doReturn(true).when(jwtTokenProvider).validateRefreshToken(refreshToken);
        doReturn(username).when(jwtTokenProvider).extractUsername(refreshToken);
        doReturn(Optional.of(user)).when(userService).getUserByUsername(username);
        doReturn(newAccessToken).when(jwtTokenProvider).generateAccessToken(user);
        doReturn(newRefreshToken).when(jwtTokenProvider).generateRefreshToken(user);

        // Act
        var result = mockMvc.perform(post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"refreshToken\": \"%s\" }", refreshToken))
        );

        // Assert
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.accessToken").value(newAccessToken));
        result.andExpect(jsonPath("$.refreshToken").value(newRefreshToken));
    }
}