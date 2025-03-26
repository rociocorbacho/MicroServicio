package com.ejemplospringboot.libros_autor.service;

import java.util.List;
import java.util.Optional;

import com.ejemplospringboot.libros_autor.dto.AuthorCreateDTO;
import com.ejemplospringboot.libros_autor.dto.AuthorDTO;
import com.ejemplospringboot.libros_autor.model.Author;

public interface AuthorServiceInterface {
    // Crear un nuevo autor
    public AuthorDTO createAuthor(AuthorCreateDTO authorDto);

    // Obtener todos los autores
    public List<AuthorDTO> getAllAuthors();

    // Buscar autor por ID
    public Optional<AuthorDTO> getAuthorById(String id);
    
    // Obtener entidad Author por ID (para uso interno)
    public Optional<Author> getAuthorEntityById(String id);

    // Actualizar autor
    public AuthorDTO updateAuthor(String id, AuthorCreateDTO authorDto);

    // Eliminar autor
    public void deleteAuthor(String id);
}