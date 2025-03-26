package com.ejemplospringboot.libros_autor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDTO {
    @NotBlank(message = "El título del libro es obligatorio")
    @Size(min = 1, max = 200, message = "El título debe tener entre 1 y 200 caracteres")
    private String title;
    
    @NotNull(message = "El ID del autor es obligatorio")
    private String authorId;
}