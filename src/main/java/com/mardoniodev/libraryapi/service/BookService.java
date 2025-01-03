package com.mardoniodev.libraryapi.service;

import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
