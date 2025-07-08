package com.maxicruz.usuarios.infraestructure.adapters.in.controllers;

import com.maxicruz.usuarios.application.services.IUserService;
import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.models.CustomUserDetails;
import com.maxicruz.usuarios.infraestructure.adapters.in.dtos.*;
import com.maxicruz.usuarios.infraestructure.adapters.in.exceptions.InvalidTokenException;
import com.maxicruz.usuarios.infraestructure.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String REFRESH_TOKEN_REQUIRED = "Refresh token is required";
    private static final String REFRESH_TOKEN_INVALID = "Invalid refresh token";
    private static final String USER_PASS_REQUIRED = "Username and password is required";
    private static final String USER_PASS_EMAIL_REQUIRED = "Username, password and email is required";

    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(IUserService userService, JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login user successfully",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "400", description = USER_PASS_REQUIRED,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            )
    })
    public AuthResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {

        if (loginRequestDTO.getUsername().isEmpty() || loginRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException(USER_PASS_REQUIRED);
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return AuthResponseDTO.builder()
                    .accessToken(jwtTokenProvider.generateAccessToken(userDetails.getUser()))
                    .refreshToken(jwtTokenProvider.generateRefreshToken(userDetails.getUser()))
                    .build();
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register user successfully",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "400", description = USER_PASS_EMAIL_REQUIRED,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            )
    })
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        if (registerRequestDTO.getUsername().isEmpty() ||
                registerRequestDTO.getPassword().isEmpty() ||
                registerRequestDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException(USER_PASS_EMAIL_REQUIRED);
        }

        User user = userService.registerUser(
                registerRequestDTO.getUsername(),
                passwordEncoder.encode(registerRequestDTO.getPassword()),
                registerRequestDTO.getEmail()
        );

        return AuthResponseDTO.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user))
                .build();
    }

    @PostMapping("refresh")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Refresh Access Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh access token successfully",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "400", description = REFRESH_TOKEN_REQUIRED,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "401", description = REFRESH_TOKEN_INVALID,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )}
            )
    })
    public AuthResponseDTO refresh(@RequestBody RefreshRequestDTO refreshRequestDTO) {

        String refreshToken = refreshRequestDTO.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException(REFRESH_TOKEN_REQUIRED);
        }

        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidTokenException(REFRESH_TOKEN_INVALID);
        }

        String username = jwtTokenProvider.extractUsername(refreshToken);
        User user = userService.getUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(USER_NOT_FOUND)
                );
        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
