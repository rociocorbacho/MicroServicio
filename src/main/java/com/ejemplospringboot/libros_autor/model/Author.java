package com.ejemplospringboot.libros_autor.model;

import java.util.UUID;

public class Author {
    private String id;
    private String name;

    // Constructor vacío necesario para JDBC
    public Author() {
        this.id = UUID.randomUUID().toString();
    }

    // Constructor con nombre
    public Author(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    // Constructor con ID y nombre (para recuperar de la base de datos)
    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + "]";
    }
}