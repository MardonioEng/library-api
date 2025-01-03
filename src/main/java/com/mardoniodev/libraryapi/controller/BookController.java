package com.mardoniodev.libraryapi.controller;

import com.mardoniodev.libraryapi.controller.dto.BookRegistrationDTO;
import com.mardoniodev.libraryapi.controller.dto.ResponseError;
import com.mardoniodev.libraryapi.exceptions.DuplicateRegisterException;
import com.mardoniodev.libraryapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BookRegistrationDTO bookRegistrationDTO) {
         try {
            return ResponseEntity.ok(bookRegistrationDTO);
         } catch (DuplicateRegisterException e) {
             var errorDTO = ResponseError.conflict(e.getMessage());
             return ResponseEntity.status(errorDTO.status()).body(errorDTO);
         }
    }
}
