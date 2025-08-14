package com.maxicruz.usuarios.domain.ports.out;

import com.maxicruz.usuarios.domain.models.User;
import com.maxicruz.usuarios.domain.ports.in.IRetrieveUser;
import com.maxicruz.usuarios.domain.ports.in.IUpdateUser;

public interface IUserRepositoryPort extends IRetrieveUser, IUpdateUser {
    User createUser(User user);
}
