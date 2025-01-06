package com.mardoniodev.libraryapi.repository;

import com.mardoniodev.libraryapi.model.Author;
import com.mardoniodev.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    boolean existsByAuthor(Author author);

    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

}
