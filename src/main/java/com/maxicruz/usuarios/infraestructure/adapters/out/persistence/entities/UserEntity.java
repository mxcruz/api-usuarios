package com.maxicruz.usuarios.infraestructure.adapters.out.persistence.entities;

import com.maxicruz.usuarios.domain.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "[user]", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", name = "user_id")
    UUID id;

    @Column
    String username;

    @Column
    String password;

    @Column
    String email;

    @Column
    LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    RoleEntity role;

    public static UserEntity of(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .createdOn(user.getCreatedOn())
                .role(RoleEntity.of(user.getRole()))
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .createdOn(createdOn)
                .role(role.toRole())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
