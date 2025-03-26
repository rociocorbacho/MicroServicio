package com.ejemplospringboot.libros_autor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplospringboot.libros_autor.dto.BookCreateDTO;
import com.ejemplospringboot.libros_autor.dto.BookDTO;
import com.ejemplospringboot.libros_autor.service.BookServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Libros", description = "API para gestionar libros")
@Validated
public class BookController {
    private final BookServiceInterface bookService;

    public BookController(BookServiceInterface bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Crear un nuevo libro", description = "Crea un nuevo libro y devuelve el objeto creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", 
                     description = "Libro creado exitosamente",
                     content = @Content(schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "400", 
                     description = "Datos de libro inválidos",
                     content = @Content),
        @ApiResponse(responseCode = "404", 
                     description = "Autor no encontrado",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @Parameter(description = "Datos del libro a crear", required = true)
            @Valid @RequestBody BookCreateDTO bookDto) {
        BookDTO createdBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los libros", description = "Devuelve una lista de todos los libros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Lista de libros recuperada exitosamente",
                     content = @Content(schema = @Schema(implementation = BookDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Obtener libro por ID", description = "Busca un libro por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Libro encontrado",
                     content = @Content(schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Libro no encontrado",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID del libro a buscar", required = true)
            @PathVariable String id) {
        return bookService.getBookById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Actualizar libro", description = "Actualiza los datos de un libro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Libro actualizado exitosamente",
                     content = @Content(schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Libro no encontrado o autor no encontrado",
                     content = @Content),
        @ApiResponse(responseCode = "400", 
                     description = "Datos de libro inválidos",
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID del libro a actualizar", required = true)
            @PathVariable String id, 
            @Parameter(description = "Nuevos datos del libro", required = true)
            @Valid @RequestBody BookCreateDTO bookDto) {
        
        if (!bookService.getBookById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        try {
            BookDTO updatedBook = bookService.updateBook(id, bookDto);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar libro", description = "Elimina un libro por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                     description = "Libro eliminado exitosamente",
                     content = @Content),
        @ApiResponse(responseCode = "404", 
                     description = "Libro no encontrado",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID del libro a eliminar", required = true)
            @PathVariable String id) {
        
        if (!bookService.getBookById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        bookService.deleteBook(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}