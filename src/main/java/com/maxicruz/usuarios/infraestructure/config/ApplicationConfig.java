package com.maxicruz.usuarios.infraestructure.config;

import com.maxicruz.usuarios.application.services.*;
import com.maxicruz.usuarios.application.services.impl.*;
import com.maxicruz.usuarios.application.usecases.*;
import com.maxicruz.usuarios.domain.ports.out.*;
import com.maxicruz.usuarios.domain.models.CustomUserDetails;
import com.maxicruz.usuarios.infraestructure.adapters.out.persistence.entities.UserEntity;
import com.maxicruz.usuarios.infraestructure.adapters.out.persistence.repositories.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public IUserRepositoryPort userRepositoryPort(
            UserRepositoryAdapter userRepositoryAdapter
    ) {
        return userRepositoryAdapter;
    }

    @Bean
    @DependsOn("loggingService")
    public IUserService userService(
            IUserRepositoryPort userRepositoryPort,
            ILoggingService loggingService) {
        return new UserService(
                new RetrieveUserUseCase(userRepositoryPort),
                new RegisterUserUseCase(userRepositoryPort),
                new UpdateUserUseCase(userRepositoryPort),
                loggingService
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    private final IUserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map((UserEntity user) -> new CustomUserDetails(user.toUser()))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
