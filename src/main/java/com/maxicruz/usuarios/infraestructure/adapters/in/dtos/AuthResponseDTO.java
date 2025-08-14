package com.maxicruz.usuarios.infraestructure.adapters.in.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    String accessToken;
    String refreshToken;
}
