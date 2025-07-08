package com.maxicruz.usuarios.domain.ports.in;

import com.maxicruz.usuarios.domain.models.User;

public interface IRegisterUser {
    User registerUser(String username, String password, String email);
}
