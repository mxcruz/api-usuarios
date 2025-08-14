package com.maxicruz.usuarios.infraestructure.adapters.out.persistence.repositories;

import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;
import com.maxicruz.usuarios.infraestructure.adapters.out.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements IUserRepositoryPort {

    private final IUserRepository userRepository;

    public UserRepositoryAdapter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EntityGraph(attributePaths = {"stickers"})
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntity::toUser).toList();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id).map(UserEntity::toUser);
    }

    @Override
    public Optional<User> getUserByUsername(String username) { return userRepository.findByUsername(username).map(UserEntity::toUser); }

    @Override
    public User createUser(User user) {
        return userRepository.save(UserEntity.of(user)).toUser();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(UserEntity.of(user)).toUser();
    }
}
