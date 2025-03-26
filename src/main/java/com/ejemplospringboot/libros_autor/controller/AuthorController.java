package com.ejemplospringboot.libros_autor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ejemplospringboot.libros_autor.dto.AuthorCreateDTO;
import com.ejemplospringboot.libros_autor.dto.AuthorDTO;
import com.ejemplospringboot.libros_autor.dto.BookDTO;
import com.ejemplospringboot.libros_autor.service.AuthorServiceInterface;
import com.ejemplospringboot.libros_autor.service.BookServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Autores", description = "API para gestionar autores")
@Validated
public class AuthorController {
    private final AuthorServiceInterface authorService;
    private final BookServiceInterface bookService;

    public AuthorController(AuthorServiceInterface authorService, BookServiceInterface bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Operation(summary = "Crear un nuevo autor")
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorCreateDTO authorDto) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los autores")
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener autor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable String id) {
        return authorService.getAuthorById(id)
                .map(author -> new ResponseEntity<>(author, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Actualizar autor")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable String id, @Valid @RequestBody AuthorCreateDTO authorDto) {
        if (!authorService.getAuthorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar autor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        if (!authorService.getAuthorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Obtener libros por autor")
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable String id) {
        if (!authorService.getAuthorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        List<BookDTO> books = bookService.getBooksByAuthorId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}