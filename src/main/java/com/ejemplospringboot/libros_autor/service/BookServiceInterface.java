package com.ejemplospringboot.libros_autor.service;

import java.util.List;
import java.util.Optional;

import com.ejemplospringboot.libros_autor.dto.BookCreateDTO;
import com.ejemplospringboot.libros_autor.dto.BookDTO;
import com.ejemplospringboot.libros_autor.model.Book;

public interface BookServiceInterface {
    // Crear un nuevo libro
    public BookDTO createBook(BookCreateDTO bookDto);

    // Obtener todos los libros
    public List<BookDTO> getAllBooks();

    // Buscar libro por ID
    public Optional<BookDTO> getBookById(String id);
    
    // Obtener entidad Book por ID (para uso interno)
    public Optional<Book> getBookEntityById(String id);

    // Actualizar libro
    public BookDTO updateBook(String id, BookCreateDTO bookDto);

    // Eliminar libro
    public void deleteBook(String id);
    
    // Obtener libros por ID de autor
    public List<BookDTO> getBooksByAuthorId(String authorId);
}