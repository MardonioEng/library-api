package com.mardoniodev.libraryapi.validator;

import com.mardoniodev.libraryapi.exceptions.DuplicateRegisterException;
import com.mardoniodev.libraryapi.model.Author;
import com.mardoniodev.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class AuthorValidator {

    private AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author) {
        if(existsAuthorSaved(author)) {
            throw new DuplicateRegisterException("Duplicate register found.");
        }
    }

    private boolean existsAuthorSaved(Author author) {
        Optional<Author> foundedAuthor = authorRepository.findByNameAndBirthdateAndNationality(
                author.getName(), author.getBirthdate(), author.getNationality()
        );

        if(author.getId() == null) {
            return foundedAuthor.isPresent();
        }

        return !author.getId().equals(foundedAuthor.get().getId()) && foundedAuthor.isPresent();
    }
}
