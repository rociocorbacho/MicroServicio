package com.ejemplospringboot.libros_autor.mapper;

import com.ejemplospringboot.libros_autor.dto.BookCreateDTO;
import com.ejemplospringboot.libros_autor.dto.BookDTO;
import com.ejemplospringboot.libros_autor.model.Author;
import com.ejemplospringboot.libros_autor.model.Book;
import com.ejemplospringboot.libros_autor.repository.AuthorRepository;

import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    
    public BookMapper(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }
    
    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        
        // Si el objeto Author ya está cargado, usarlo directamente
        Author author = book.getAuthor();
        
        // Si no está cargado, buscarlo en la base de datos
        if (author == null && book.getAuthorId() != null) {
            author = authorRepository.findById(book.getAuthorId()).orElse(null);
            book.setAuthor(author);
        }
        
        return new BookDTO(
            book.getId(),
            book.getTitle(),
            author != null ? authorMapper.toDTO(author) : null
        );
    }
    
    public Book toEntity(BookCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthorId(dto.getAuthorId());
        
        // Cargar el autor si es posible
        authorRepository.findById(dto.getAuthorId())
            .ifPresent(book::setAuthor);
            
        return book;
    }
    
    public void updateEntityFromDTO(BookCreateDTO dto, Book book) {
        if (dto == null || book == null) {
            return;
        }
        
        book.setTitle(dto.getTitle());
        
        // Solo actualizar el autor si ha cambiado
        if (dto.getAuthorId() != null && !dto.getAuthorId().equals(book.getAuthorId())) {
            book.setAuthorId(dto.getAuthorId());
            authorRepository.findById(dto.getAuthorId())
                .ifPresent(book::setAuthor);
        }
    }
}