package com.ejemplospringboot.libros_autor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API de Libros y Autores")
                        .description("API RESTful para gestionar libros y autores usando Spring Boot y JDBC Template")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Desarrollador")
                                .email("contacto@ejemplo.com")
                                .url("https://ejemplo.com"))
                        .license(new License()
                                .name("Licencia Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(Arrays.asList(
                        new Server().url("/").description("Servidor local"),
                        new Server().url("https://api.ejemplo.com").description("Servidor de producción")));
    }
}