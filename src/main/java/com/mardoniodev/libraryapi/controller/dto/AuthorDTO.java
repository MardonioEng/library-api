package com.mardoniodev.libraryapi.controller.dto;

import com.mardoniodev.libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        String name,
        LocalDate birthdate,
        String nationality
) {
    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthdate(this.birthdate);
        author.setNationality(this.nationality);
        return author;
    }
}
