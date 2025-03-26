package com.ejemplospringboot.libros_autor.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ejemplospringboot.libros_autor.model.Author;

@Repository
public class AuthorRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    // RowMapper para convertir filas de la base de datos a objetos Author
    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> 
        new Author(
            rs.getString("id"),
            rs.getString("name")
        );
    
    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public Author save(Author author) {
        // Si el ID es null, generamos uno nuevo
        if (author.getId() == null || author.getId().isEmpty()) {
            author.setId(UUID.randomUUID().toString());
        }
        
        // Verificamos si el autor ya existe
        if (existsById(author.getId())) {
            // Actualizamos el autor existente
            String sql = "UPDATE author SET name = ? WHERE id = ?";
            int updated = jdbcTemplate.update(sql, author.getName(), author.getId());
            if (updated == 0) {
                throw new RuntimeException("No se pudo actualizar el autor");
            }
        } else {
            // Creamos un nuevo autor
            String sql = "INSERT INTO author (id, name) VALUES (?, ?)";
            int inserted = jdbcTemplate.update(sql, author.getId(), author.getName());
            if (inserted == 0) {
                throw new RuntimeException("No se pudo crear el autor");
            }
        }
        
        return author;
    }
    
    public List<Author> findAll() {
        String sql = "SELECT id, name FROM author";
        return jdbcTemplate.query(sql, authorRowMapper);
    }
    
    public Optional<Author> findById(String id) {
        try {
            String sql = "SELECT id, name FROM author WHERE id = ?";
            Author author = jdbcTemplate.queryForObject(sql, authorRowMapper, id);
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM author WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
    public void deleteById(String id) {
        // Primero eliminamos los libros asociados al autor
        String deleteBooksSQL = "DELETE FROM book WHERE author_id = ?";
        jdbcTemplate.update(deleteBooksSQL, id);
        
        // Luego eliminamos el autor
        String deleteAuthorSQL = "DELETE FROM author WHERE id = ?";
        jdbcTemplate.update(deleteAuthorSQL, id);
    }
    
    public int count() {
        String sql = "SELECT COUNT(*) FROM author";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}