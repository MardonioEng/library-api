package com.mardoniodev.libraryapi.controller.mappers;

import com.mardoniodev.libraryapi.controller.dto.BookRegistrationDTO;
import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java(authorRepository.findById(bookRegistrationDTO.authorID()).orElse(null))")
    public abstract Book toEntity(BookRegistrationDTO bookRegistrationDTO);

}
