package com.ejemplospringboot.libros_autor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private String id;
    private String name;
    private int bookCount;
    
    // Constructor sin bookCount para casos donde no se necesita ese dato
    public AuthorDTO(String id, String name) {
        this.id = id;
        this.name = name;
        this.bookCount = 0;
    }
}