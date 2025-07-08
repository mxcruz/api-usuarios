package com.maxicruz.usuarios.infraestructure.adapters.in.dtos;

import com.maxicruz.usuarios.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private String username;
    private String email;
    private LocalDateTime createdOn;

    public static UserResponseDTO from(User user) {
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .createdOn(user.getCreatedOn())
                .build();
    }
}
