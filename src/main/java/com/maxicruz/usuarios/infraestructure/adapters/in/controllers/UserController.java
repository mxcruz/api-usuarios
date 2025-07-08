package com.maxicruz.usuarios.infraestructure.adapters.in.controllers;

import com.maxicruz.usuarios.application.services.IUserService;
import com.maxicruz.usuarios.domain.models.CustomUserDetails;
import com.maxicruz.usuarios.infraestructure.adapters.in.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final String NO_SUCH_USER_EXISTS =  "No existe tal usuario";
    private static final String INVALID_TOKEN =  "Token is invalid";

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of Users",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )}
            )
    })
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(UserResponseDTO::from).toList();
    }

    @Operation(summary = "Get User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "204", description = NO_SUCH_USER_EXISTS,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoSuchElementException.class)) })
    })
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable UUID id) {
        var user = userService.getUserById(id).orElseThrow(
                () -> new NoSuchElementException(NO_SUCH_USER_EXISTS)
        );
        return UserResponseDTO.from(user);
    }

    @Operation(summary = "Get User Data by Token Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session User Data",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "204", description = NO_SUCH_USER_EXISTS,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoSuchElementException.class)) }),
            @ApiResponse(responseCode = "401", description = INVALID_TOKEN,
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SecurityException.class)) })
    })
    @GetMapping("/me")
    public UserResponseDTO getUserData() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication == null) { throw new SecurityException(INVALID_TOKEN); }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (userDetails == null || userDetails.getUser() == null) {
            throw new NoSuchElementException(NO_SUCH_USER_EXISTS);
        }

        return UserResponseDTO.from(
                userDetails.getUser()
        );
    }
}
