package com.mardoniodev.libraryapi.controller.mappers;

import com.mardoniodev.libraryapi.controller.dto.AuthorDTO;
import com.mardoniodev.libraryapi.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO authorDTO);

    AuthorDTO toDto(Author author);

}
