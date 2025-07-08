package com.maxicruz.usuarios.application.services;

import com.maxicruz.usuarios.domain.ports.in.IRetrieveUser;
import com.maxicruz.usuarios.domain.ports.in.IRegisterUser;
import com.maxicruz.usuarios.domain.ports.in.IUpdateUser;

public interface IUserService extends IRetrieveUser, IRegisterUser, IUpdateUser { }
