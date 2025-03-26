package com.ejemplospringboot.libros_autor.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ejemplospringboot.libros_autor.dto.AuthorCreateDTO;
import com.ejemplospringboot.libros_autor.dto.AuthorDTO;
import com.ejemplospringboot.libros_autor.dto.BookDTO;
import com.ejemplospringboot.libros_autor.service.AuthorServiceInterface;
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

    @Operation(summary = "Crear un nuevo autor", description = "Crea un nuevo autor y devuelve el objeto creado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", 
                     description = "Autor creado exitosamente",
                     content = @Content(schema = @Schema(implementation = AuthorDTO.class))),
        @ApiResponse(responseCode = "400", 
                     description = "Datos de autor inválidos",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(
            @Parameter(description = "Datos del autor a crear", required = true)
            @Valid @RequestBody AuthorCreateDTO authorDto) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los autores", description = "Devuelve una lista de todos los autores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Lista de autores recuperada exitosamente",
                     content = @Content(schema = @Schema(implementation = AuthorDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Operation(summary = "Obtener autor por ID", description = "Busca un autor por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Autor encontrado",
                     content = @Content(schema = @Schema(implementation = AuthorDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Autor no encontrado",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(
            @Parameter(description = "ID del autor a buscar", required = true)
            @PathVariable String id) {
        return authorService.getAuthorById(id)
                .map(author -> new ResponseEntity<>(author, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Actualizar autor", description = "Actualiza los datos de un autor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Autor actualizado exitosamente",
                     content = @Content(schema = @Schema(implementation = AuthorDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Autor no encontrado",
                     content = @Content),
        @ApiResponse(responseCode = "400", 
                     description = "Datos de autor inválidos",
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @Parameter(description = "ID del autor a actualizar", required = true)
            @PathVariable String id, 
            @Parameter(description = "Nuevos datos del autor", required = true)
            @Valid @RequestBody AuthorCreateDTO authorDto) {
        
        if (!authorService.getAuthorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar autor", description = "Elimina un autor por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                     description = "Autor eliminado exitosamente",
                     content = @Content),
        @ApiResponse(responseCode = "404", 
                     description = "Autor no encontrado",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID del autor a eliminar", required = true)
            @PathVariable String id) {
        
        if (!authorService.getAuthorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        authorService.deleteAuthor(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Obtener libros por autor", description = "Obtiene todos los libros escritos por un autor específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Lista de libros recuperada exitosamente",
                     content = @Content(schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Autor no encontrado",
                     content = @Content)
    })
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(
            @Parameter(description = "ID del autor", required = true)
            @PathVariable String id) {
        
        if (!authorService.getAuthorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        List<BookDTO> books = bookService.getBooksByAuthorId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}