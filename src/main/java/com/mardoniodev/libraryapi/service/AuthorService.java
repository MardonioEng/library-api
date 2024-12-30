package com.mardoniodev.libraryapi.service;

import com.mardoniodev.libraryapi.exceptions.OperationNotAllowedException;
import com.mardoniodev.libraryapi.model.Author;
import com.mardoniodev.libraryapi.repository.AuthorRepository;
import com.mardoniodev.libraryapi.repository.BookRepository;
import com.mardoniodev.libraryapi.validator.AuthorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorValidator authorValidator;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, AuthorValidator authorValidator) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorValidator = authorValidator;
    }

    public List<Author> findAuthors(String name, String nationality) {
        return authorRepository.findByNameAndNationality(name, nationality);
    }

    public Optional<Author> findById(UUID id) {
        return  authorRepository.findById(id);
    }

    public void save(Author author) {
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public void update(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o Autor já esteja salvo na base.");
        }
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public void deleteAuthor(Author author) {
        if(containsBook(author)) {
            throw new OperationNotAllowedException("Autor possi livros cadastrados.");
        }
        authorRepository.delete(author);
    }

    public boolean containsBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
