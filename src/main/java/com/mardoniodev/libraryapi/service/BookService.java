package com.mardoniodev.libraryapi.service;

import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.model.GenreEnum;
import com.mardoniodev.libraryapi.repository.BookRepository;
import com.mardoniodev.libraryapi.repository.specs.BookSpecs;
import com.mardoniodev.libraryapi.validator.BookValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public BookService(BookRepository bookRepository, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    public List<Book> findAuthors(
            String isbn, String title, String authorName, GenreEnum genre, Integer publicationYear
    ) {
        Specification<Book> specs = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(isbn != null) {
            specs = specs.and(BookSpecs.isbnEqual(isbn));
        }

        if(title != null) {
            specs = specs.and(BookSpecs.titleLike(title));
        }

        if(genre != null) {
            specs = specs.and(BookSpecs.genreEqual(genre));
        }

        if(publicationYear != null) {
            specs = specs.and(BookSpecs.publicationYearEqual(publicationYear));
        }

        if(authorName != null) {
            specs = specs.and(BookSpecs.authorNameLike(authorName));
        }

        return bookRepository.findAll(specs);
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        bookValidator.validateBook(book);
        return bookRepository.save(book);
    }

    public void updateBook(Book book) {
        if(book.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o Livro já esteja salvo na base.");
        }
        bookValidator.validateBook(book);
        bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

}
