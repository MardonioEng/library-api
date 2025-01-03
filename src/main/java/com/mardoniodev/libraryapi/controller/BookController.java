package com.mardoniodev.libraryapi.controller;

import com.mardoniodev.libraryapi.controller.dto.BookRegistrationDTO;
import com.mardoniodev.libraryapi.controller.mappers.BookMapper;
import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody @Valid BookRegistrationDTO bookRegistrationDTO) {
        Book book = bookMapper.toEntity(bookRegistrationDTO);
        bookService.saveBook(book);
        URI location = this.generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }
}
