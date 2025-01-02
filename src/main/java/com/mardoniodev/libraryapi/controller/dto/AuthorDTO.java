package com.mardoniodev.libraryapi.controller.dto;

import com.mardoniodev.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(

        UUID id,

        @NotBlank(message = "Field required")
        @Size(min = 2, max = 100, message = "field outside the standard size")
        String name,

        @NotNull(message = "Field required")
        @Past(message = "Cannot be future date")
        LocalDate birthdate,

        @NotBlank(message = "Field required")
        @Size(min = 2, max = 50, message = "field outside the standard size")
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
