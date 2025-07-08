package com.maxicruz.usuarios.domain.ports.in;

import com.maxicruz.usuarios.domain.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRetrieveUser {
    List<User> getAllUsers();
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByUsername(String username);
}
