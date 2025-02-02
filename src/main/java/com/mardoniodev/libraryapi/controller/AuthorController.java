package com.mardoniodev.libraryapi.controller;

import com.mardoniodev.libraryapi.controller.dto.AuthorDTO;
import com.mardoniodev.libraryapi.controller.dto.ResponseError;
import com.mardoniodev.libraryapi.controller.mappers.AuthorMapper;
import com.mardoniodev.libraryapi.exceptions.DuplicateRegisterException;
import com.mardoniodev.libraryapi.exceptions.OperationNotAllowedException;
import com.mardoniodev.libraryapi.model.Author;
import com.mardoniodev.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper, MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.mappingJackson2HttpMessageConverter = mappingJackson2HttpMessageConverter;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<Author> authors = authorService.findAuthors(name, nationality);
        List<AuthorDTO> list = authors
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID idAuthor = UUID.fromString(id);
        return authorService
                .findById(idAuthor)
                .map(author -> {
                    AuthorDTO authorDTO = authorMapper.toDto(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.save(author);
        URI location = this.generateHeaderLocation(author.getId());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO) {
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
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(idAuthor);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorService.deleteAuthor(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

}
