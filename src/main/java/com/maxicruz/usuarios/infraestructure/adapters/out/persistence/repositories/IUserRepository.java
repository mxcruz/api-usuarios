package com.maxicruz.usuarios.infraestructure.adapters.out.persistence.repositories;

import com.maxicruz.usuarios.infraestructure.adapters.out.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
}
