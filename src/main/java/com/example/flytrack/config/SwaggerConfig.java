package com.example.flytrack.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI flyTrackOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("FlyTrack API – AeroPuerto Smart")
                        .description("API REST para el sistema de gestión de vuelos FlyTrack. " +
                                "Permite consultar itinerarios, recibir notificaciones en tiempo real " +
                                "vía WebSocket, consultar el estado del equipaje y reportar inconvenientes.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipo DevOps – AeroPuerto Smart")
                                .email("devops@aeropuertosmart.com")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingresa el token JWT obtenido desde /auth/login")));
    }
}
