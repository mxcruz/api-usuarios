package com.maxicruz.usuarios.infraestructure.adapters.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxicruz.usuarios.application.services.*;
import com.maxicruz.usuarios.domain.models.*;
import com.maxicruz.usuarios.infraestructure.adapters.in.dtos.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UsersBootstrap implements InitializingBean {

    @Value("${data.initialization.enabled:false}")
    private boolean isDataInitializationEnabled;

    @Value("${data.initialization.path:data/test}")
    private String pathDataInitialization;

    static final String USER_FILE_NAME = "users.json";
    static final int FIGURINES_BATCH_SIZE = 100;

    final IUserService userService;
    final PasswordEncoder passwordEncoder;

    public UsersBootstrap(IUserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!isDataInitializationEnabled) return;

        // Genera catalogo de datos
        List<User> users = createUsers();
    }

    private List<User> createUsers() throws IOException {

        List<User> usersCreated = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(pathDataInitialization + "/" + USER_FILE_NAME);

        List<RegisterRequestDTO> users = mapper.readValue(file, new TypeReference<>() { });

        users.forEach(user -> usersCreated.add(userService.registerUser(
                        user.getUsername(),
                        passwordEncoder.encode(user.getPassword()),
                        user.getEmail()
                )
        ));

        return usersCreated;
    }
}
