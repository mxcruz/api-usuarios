package com.maxicruz.usuarios.infraestructure.adapters.out.persistence.entities;

import com.maxicruz.usuarios.domain.models.Role;

public enum RoleEntity {
    ADMIN,
    USER;

    public static RoleEntity of(Role role) {
        return RoleEntity.valueOf(role.toString());
    }

    public Role toRole() {
        return Role.valueOf(this.toString());
    }
}
