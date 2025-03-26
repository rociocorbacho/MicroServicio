package com.ejemplospringboot.libros_autor.model;

import java.util.UUID;

public class Book {
    private String id;
    private String title;
    private String authorId;  // Solo almacenamos el ID del autor
    private Author author;    // Objeto completo del autor (para relaciones)

    // Constructor vacío necesario para JDBC
    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    // Constructor con título y autor
    public Book(String title, Author author) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.authorId = author.getId();
    }

    // Constructor con ID, título y authorId (para recuperar de la base de datos)
    public Book(String id, String title, String authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
        if (author != null) {
            this.authorId = author.getId();
        }
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", authorId=" + authorId + "]";
    }
}