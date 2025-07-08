package com.maxicruz.usuarios.infraestructure.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authRequest ->
                            authRequest
                                    .requestMatchers("/auth/**").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .sessionManagement(sessionManager ->
                            sessionManager
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    @Profile({"it", "dev"})
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Ignora las rutas de Swagger y Actuator en los perfiles de desarrollo y prueba
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-ui/**", "/api-docs/**", "/swagger-ui.html",  "/actuator/**");
    }
}
