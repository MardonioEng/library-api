package com.mardoniodev.libraryapi.controller;

import com.mardoniodev.libraryapi.controller.dto.AuthorDTO;
import com.mardoniodev.libraryapi.controller.dto.ResponseError;
import com.mardoniodev.libraryapi.exceptions.DuplicateRegisterException;
import com.mardoniodev.libraryapi.exceptions.OperationNotAllowedException;
import com.mardoniodev.libraryapi.model.Author;
import com.mardoniodev.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<Author> authors = authorService.findAuthors(name, nationality);
        List<AuthorDTO> list = authors
                .stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthdate(),
                        author.getNationality())
                ).collect(Collectors.toList());
        return  ResponseEntity.ok(list);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(idAuthor);
        if(authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO dto =  new AuthorDTO(
                    author.getId(),
                    author.getName(),
                    author.getBirthdate(),
                    author.getNationality()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        try {
            Author entityAuthor = authorDTO.mapToAuthor();
            authorService.save(entityAuthor);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(entityAuthor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRegisterException e) {
            var errorDTO = ResponseError.conflict("Duplicate author");
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO) {
        try {
            UUID idAuthor = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(idAuthor);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Author author = authorOptional.get();
            author.setName(authorDTO.name());
            author.setBirthdate(authorDTO.birthdate());
            author.setNationality(authorDTO.nationality());

            authorService.update(author);

            return ResponseEntity.noContent().build();
        } catch (DuplicateRegisterException e) {
            var errorDTO = ResponseError.conflict("Duplicate author");
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id) {
        try {
            UUID idAuthor = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(idAuthor);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            authorService.deleteAuthor(authorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e) {
            var errorDTO = ResponseError.defaultError(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

}
