package com.ejemplospringboot.libros_autor.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejemplospringboot.libros_autor.dto.AuthorCreateDTO;
import com.ejemplospringboot.libros_autor.dto.AuthorDTO;
import com.ejemplospringboot.libros_autor.exception.ResourceNotFoundException;
import com.ejemplospringboot.libros_autor.mapper.AuthorMapper;
import com.ejemplospringboot.libros_autor.model.Author;
import com.ejemplospringboot.libros_autor.repository.AuthorRepository;

@Service
public class AuthorService implements AuthorServiceInterface {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional
    public AuthorDTO createAuthor(AuthorCreateDTO authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDTO(savedAuthor);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(String id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDTO);
    }
    
    @Override
    public Optional<Author> getAuthorEntityById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional
    public AuthorDTO updateAuthor(String id, AuthorCreateDTO authorDto) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    authorMapper.updateEntityFromDTO(authorDto, existingAuthor);
                    Author updatedAuthor = authorRepository.save(existingAuthor);
                    return authorMapper.toDTO(updatedAuthor);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "id", id));
    }

    @Override
    @Transactional
    public void deleteAuthor(String id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Autor", "id", id);
        }
    }
}