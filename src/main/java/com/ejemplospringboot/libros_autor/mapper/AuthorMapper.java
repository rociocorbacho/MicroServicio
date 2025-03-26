package com.ejemplospringboot.libros_autor.mapper;

import com.ejemplospringboot.libros_autor.dto.AuthorCreateDTO;
import com.ejemplospringboot.libros_autor.dto.AuthorDTO;
import com.ejemplospringboot.libros_autor.model.Author;
import com.ejemplospringboot.libros_autor.repository.BookRepository;

import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    
    private final BookRepository bookRepository;
    
    public AuthorMapper(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public AuthorDTO toDTO(Author author) {
        if (author == null) {
            return null;
        }
        
        // Contar libros del autor
        int bookCount = bookRepository.countByAuthorId(author.getId());
        
        return new AuthorDTO(
            author.getId(),
            author.getName(),
            bookCount
        );
    }
    
    public Author toEntity(AuthorCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Author author = new Author();
        author.setName(dto.getName());
        return author;
    }
    
    public void updateEntityFromDTO(AuthorCreateDTO dto, Author author) {
        if (dto == null || author == null) {
            return;
        }
        
        author.setName(dto.getName());
    }
}