package com.businesslogic.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Bank system API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Alisha Shelby"
                )
        )
)
public class OpenApiConfig {
}
