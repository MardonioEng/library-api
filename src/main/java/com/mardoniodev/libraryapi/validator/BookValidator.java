package com.mardoniodev.libraryapi.validator;

import com.mardoniodev.libraryapi.exceptions.DuplicateRegisterException;
import com.mardoniodev.libraryapi.exceptions.InvalidFieldException;
import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookValidator {

    private static final int YEAR_REQUIREMENT_PRICE = 2020;

    private final BookRepository bookRepository;

    public BookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void validateBook(Book book) {
        if(existsBookWithIsbn(book)) {
         throw new DuplicateRegisterException("ISBN already exists");
        }

        if(isRequiredPriceNull(book)) {
            throw new InvalidFieldException("price", "Para livros com anos de publicação a partir de 2020, o preço é obrigatório.");
        }
    }

    private boolean isRequiredPriceNull(Book book) {
        return book.getPrice() == null &&
                book.getPublicationDate().getYear() >= YEAR_REQUIREMENT_PRICE;
    }

    private boolean existsBookWithIsbn(Book book) {
        Optional<Book> bookFounded = bookRepository.findByIsbn(book.getIsbn());
        if(book.getIsbn() == null) {
            return bookFounded.isPresent();
        }
        return bookFounded
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }

}
