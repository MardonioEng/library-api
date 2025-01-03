package com.mardoniodev.libraryapi.controller.dto;

import com.mardoniodev.libraryapi.model.GenreEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRegistrationDTO(
        @NotBlank(message = "Required field")
        @ISBN
        String isbn,

        @NotBlank(message = "Required field")
        String title,

        @NotNull(message = "Required field")
        @Past(message = "Cannot be future date")
        LocalDate publicationDate,

        GenreEnum genre,

        BigDecimal price,

        @NotNull(message = "required field")
        UUID authorID
) {
}
