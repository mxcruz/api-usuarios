package com.maxicruz.usuarios.infraestructure.config;

import com.maxicruz.usuarios.infraestructure.adapters.out.loggin.Slf4jLoggingAdapter;
import com.maxicruz.usuarios.application.services.ILoggingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogginConfig {
    @Bean
    public ILoggingService loggingService() {
        return new Slf4jLoggingAdapter();
    }
}
