package com.maxicruz.usuarios.application.services.impl;

import com.maxicruz.usuarios.application.services.ILoggingService;
import com.maxicruz.usuarios.application.services.IUserService;
import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.in.IRegisterUser;
import com.maxicruz.usuarios.domain.ports.in.IRetrieveUser;
import com.maxicruz.usuarios.domain.ports.in.IUpdateUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements IUserService {

    private final IRetrieveUser retrieveUser;
    private final IRegisterUser registerUser;
    private final IUpdateUser updateUser;
    private final ILoggingService loggingService;

    public UserService(IRetrieveUser retrieveUser,
                       IRegisterUser registerUser,
                       IUpdateUser updateUser,
                       ILoggingService loggingService) {
        this.retrieveUser = retrieveUser;
        this.registerUser = registerUser;
        this.updateUser = updateUser;
        this.loggingService = loggingService;
    }

    @Override
    public List<User> getAllUsers() {
        loggingService.logDebug("User getAllUsers()");
        return retrieveUser.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        loggingService.logDebug("User getUserById({})", id);
        return retrieveUser.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        loggingService.logDebug("User getUserByUsername({})", username);
        return retrieveUser.getUserByUsername(username);
    }

    @Override
    public User registerUser(String username, String password, String email) {
        loggingService.logDebug("User registerUser({}, [password], {})", username, email);
        return registerUser.registerUser(username, password, email);
    }

    @Override
    public User updateUser(User user) {
        loggingService.logDebug("User updateUser({})", user);
        return updateUser.updateUser(user);
    }
}