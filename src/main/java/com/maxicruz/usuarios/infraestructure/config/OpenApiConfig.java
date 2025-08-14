package com.maxicruz.usuarios.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private final String appApiName;
    private final String appApiVersion;

    public OpenApiConfig(
            @Value("${info.application.name}") String appApiName,
            @Value("${info.doc.version}") String appApiVersion
    ) {
        this.appApiName = appApiName;
        this.appApiVersion = appApiVersion;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title(appApiName).version(appApiVersion));
    }
}