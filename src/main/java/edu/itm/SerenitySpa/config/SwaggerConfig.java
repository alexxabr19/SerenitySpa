package edu.itm.SerenitySpa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Serenity SPA API",
                version = "1.0",
                description = "API para la gestión de clientes, servicios, empleados, sedes y citas del Serenity SPA"
        )
)
public class SwaggerConfig {
}
