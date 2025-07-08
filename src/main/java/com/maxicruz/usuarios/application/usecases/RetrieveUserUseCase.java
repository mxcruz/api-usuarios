package com.maxicruz.usuarios.application.usecases;

import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.in.IRetrieveUser;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RetrieveUserUseCase implements IRetrieveUser {

    private final IUserRepositoryPort userRepository;

    public RetrieveUserUseCase(IUserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
