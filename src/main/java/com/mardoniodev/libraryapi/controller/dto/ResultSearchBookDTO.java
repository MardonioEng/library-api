package com.mardoniodev.libraryapi.controller.dto;

import com.mardoniodev.libraryapi.model.GenreEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultSearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publishedDate,
        GenreEnum genre,
        BigDecimal price,
        AuthorDTO authorDTO
) {
}
