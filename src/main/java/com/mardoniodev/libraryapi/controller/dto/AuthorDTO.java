package com.mardoniodev.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(

        UUID id,

        @NotBlank(message = "Required file")
        @Size(min = 2, max = 100, message = "field outside the standard size")
        String name,

        @NotNull(message = "Required file")
        @Past(message = "Cannot be future date")
        LocalDate birthdate,

        @NotBlank(message = "Required file")
        @Size(min = 2, max = 50, message = "Field outside the standard size")
        String nationality
) {}
