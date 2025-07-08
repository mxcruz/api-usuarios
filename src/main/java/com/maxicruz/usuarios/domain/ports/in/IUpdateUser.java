package com.maxicruz.usuarios.domain.ports.in;

import com.maxicruz.usuarios.domain.models.User;

public interface IUpdateUser {
    User updateUser(User user);
}
