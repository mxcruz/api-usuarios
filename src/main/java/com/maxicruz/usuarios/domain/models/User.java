package com.maxicruz.usuarios.domain.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdOn;
    private Role role;

    public static class Builder {
        public User build() {
            return new User(id, username, password, email, createdOn, role);
        }
    }
}
