package com.maxicruz.usuarios.application.usecases;

import com.maxicruz.usuarios.domain.models.Role;
import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.in.IRegisterUser;
import com.maxicruz.usuarios.domain.ports.out.IUserRepositoryPort;

import java.time.LocalDateTime;

public class RegisterUserUseCase implements IRegisterUser {

    private final IUserRepositoryPort userRepository;

    public RegisterUserUseCase(IUserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String username, String password, String email) {

        return userRepository.createUser(User.builder()
                .username(username)
                .password(password)
                .email(email)
                .createdOn(LocalDateTime.now())
                .role(Role.USER)
                .build());
    }
}
