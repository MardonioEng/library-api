package com.mardoniodev.libraryapi.service;

import com.mardoniodev.libraryapi.model.Author;
import com.mardoniodev.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> findAuthors(String name, String nationality) {
        return authorRepository.findByNameAndNationality(name, nationality);
    }

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> findById(UUID id) {
        return  authorRepository.findById(id);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public void update(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o Autor já esteja salvo na base.");
        }
        authorRepository.save(author);
    }

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }
}
