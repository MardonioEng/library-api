package com.mardoniodev.libraryapi.controller;

import com.mardoniodev.libraryapi.controller.dto.BookRegistrationDTO;
import com.mardoniodev.libraryapi.controller.dto.ResultSearchBookDTO;
import com.mardoniodev.libraryapi.controller.mappers.BookMapper;
import com.mardoniodev.libraryapi.controller.mappers.BookMapperImpl;
import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.model.GenreEnum;
import com.mardoniodev.libraryapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookMapperImpl bookMapperImpl;

    public BookController(BookService bookService, BookMapper bookMapper, BookMapperImpl bookMapperImpl) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.bookMapperImpl = bookMapperImpl;
    }

    @GetMapping
    public ResponseEntity<Page<ResultSearchBookDTO>> searchBooks(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "genre", required = false) GenreEnum genre,
            @RequestParam(value = "publication-year", required = false) Integer publicationYear,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ) {
        var pageResult = bookService.findBooks(isbn, title, authorName, genre, publicationYear, pageNumber, pageSize);

        Page<ResultSearchBookDTO> resultDto = pageResult.map(bookMapper::toDto);

        return ResponseEntity.ok().body(resultDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultSearchBookDTO> getBookById(@PathVariable("id") String id) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                   var dto =  bookMapper.toDto(book);
                   return ResponseEntity.ok().body(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody @Valid BookRegistrationDTO bookRegistrationDTO) {
        Book book = bookMapper.toEntity(bookRegistrationDTO);
        bookService.saveBook(book);
        URI location = this.generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editBook(@PathVariable("id") String id, @RequestBody @Valid BookRegistrationDTO bookRegistrationDTO) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    Book entity = bookMapper.toEntity(bookRegistrationDTO);
                    book.setPublicationDate(entity.getPublicationDate());
                    book.setGenre(entity.getGenre());
                    book.setAuthor(entity.getAuthor());
                    book.setIsbn(entity.getIsbn());
                    book.setPrice(entity.getPrice());
                    book.setTitle(entity.getTitle());
                    bookService.updateBook(book);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable("id") String id) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    bookService.deleteBook(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
