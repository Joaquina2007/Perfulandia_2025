package com.Perfulandia_2025.Perfulandia_2025.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI perfulandiaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Perfulandia 2025 API")
                        .description("Documentación de la API REST para gestión de clientes")
                        .version("v1.0"));
    }
}
