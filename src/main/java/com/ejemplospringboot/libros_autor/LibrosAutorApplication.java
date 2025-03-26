package com.ejemplospringboot.libros_autor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ejemplospringboot.libros_autor"})
@OpenAPIDefinition(
    info = @Info(
        title = "API de Libros y Autores",
        version = "1.0.0",
        description = "Microservicio para gestionar libros y autores utilizando JDBC Template"
    )
)
public class LibrosAutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrosAutorApplication.class, args);
    }
}