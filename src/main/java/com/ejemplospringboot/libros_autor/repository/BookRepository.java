package com.ejemplospringboot.libros_autor.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ejemplospringboot.libros_autor.model.Book;

@Repository
public class BookRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    // RowMapper para convertir filas de la base de datos a objetos Book
    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> 
        new Book(
            rs.getString("id"),
            rs.getString("title"),
            rs.getString("author_id")
        );
    
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public int countByAuthorId(String authorId) {
        String sql = "SELECT COUNT(*) FROM book WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, authorId);
    }
    
    public Book save(Book book) {
        // Si el ID es null, generamos uno nuevo
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(UUID.randomUUID().toString());
        }
        
        // Verificamos si el libro ya existe
        if (existsById(book.getId())) {
            // Actualizamos el libro existente (solo el título, mantener el autor)
            String sql = "UPDATE book SET title = ? WHERE id = ?";
            int updated = jdbcTemplate.update(sql, book.getTitle(), book.getId());
            if (updated == 0) {
                throw new RuntimeException("No se pudo actualizar el libro");
            }
        } else {
            // Creamos un nuevo libro
            String sql = "INSERT INTO book (id, title, author_id) VALUES (?, ?, ?)";
            int inserted = jdbcTemplate.update(sql, book.getId(), book.getTitle(), book.getAuthorId());
            if (inserted == 0) {
                throw new RuntimeException("No se pudo crear el libro");
            }
        }
        
        return book;
    }
    
    public List<Book> findAll() {
        String sql = "SELECT id, title, author_id FROM book";
        return jdbcTemplate.query(sql, bookRowMapper);
    }
    
    public Optional<Book> findById(String id) {
        try {
            String sql = "SELECT id, title, author_id FROM book WHERE id = ?";
            Book book = jdbcTemplate.queryForObject(sql, bookRowMapper, id);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public List<Book> findByAuthorId(String authorId) {
        String sql = "SELECT id, title, author_id FROM book WHERE author_id = ?";
        return jdbcTemplate.query(sql, bookRowMapper, authorId);
    }
    
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM book WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
    public void deleteById(String id) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public int count() {
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}