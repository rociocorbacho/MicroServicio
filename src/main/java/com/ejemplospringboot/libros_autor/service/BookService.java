package com.ejemplospringboot.libros_autor.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejemplospringboot.libros_autor.dto.BookCreateDTO;
import com.ejemplospringboot.libros_autor.dto.BookDTO;
import com.ejemplospringboot.libros_autor.exception.ResourceNotFoundException;
import com.ejemplospringboot.libros_autor.mapper.BookMapper;
import com.ejemplospringboot.libros_autor.model.Book;
import com.ejemplospringboot.libros_autor.repository.AuthorRepository;
import com.ejemplospringboot.libros_autor.repository.BookRepository;

@Service
public class BookService implements BookServiceInterface {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public BookDTO createBook(BookCreateDTO bookDto) {
        // Verificar que el autor existe
        if (!authorRepository.existsById(bookDto.getAuthorId())) {
            throw new ResourceNotFoundException("Autor", "id", bookDto.getAuthorId());
        }
        
        // Convertir DTO a entidad y guardar
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        
        // Convertir entidad guardada a DTO y retornar
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getBookById(String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO);
    }
    
    @Override
    public Optional<Book> getBookEntityById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public BookDTO updateBook(String id, BookCreateDTO bookDto) {
        // Verificar que el autor existe
        if (!authorRepository.existsById(bookDto.getAuthorId())) {
            throw new ResourceNotFoundException("Autor", "id", bookDto.getAuthorId());
        }
        
        return bookRepository.findById(id)
                .map(existingBook -> {
                    bookMapper.updateEntityFromDTO(bookDto, existingBook);
                    Book updatedBook = bookRepository.save(existingBook);
                    return bookMapper.toDTO(updatedBook);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Libro", "id", id));
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Libro", "id", id);
        }
    }
    
    @Override
    public List<BookDTO> getBooksByAuthorId(String authorId) {
        // Verificar que el autor existe
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Autor", "id", authorId);
        }
        
        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
}