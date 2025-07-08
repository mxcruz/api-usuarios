package com.maxicruz.usuarios.application.usecases;

import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.in.IUpdateUser;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;

public class UpdateUserUseCase implements IUpdateUser {

    private final IUserRepositoryPort userRepository;

    public UpdateUserUseCase(IUserRepositoryPort teamRepository) {
        this.userRepository = teamRepository;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }
}
