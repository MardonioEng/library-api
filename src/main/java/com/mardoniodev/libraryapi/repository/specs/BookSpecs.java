package com.mardoniodev.libraryapi.repository.specs;

import com.mardoniodev.libraryapi.model.Book;
import com.mardoniodev.libraryapi.model.GenreEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genreEqual(GenreEnum genre) {
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publicationYearEqual(Integer publicationYear) {
        // and to_char(publication_date, 'YYYY') = :publicationYear
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("publicationDate"), cb.literal("YYYY")), publicationYear.toString());
    }

    public static Specification<Book> authorNameLike(String authorName) {
        return (root, query, cb) -> {
//            return cb.like(cb.upper(root.get("author").get("name")), "%" + authorName.toUpperCase() + "%");
            Join<Object, Object> joinAuthor = root.join("author", JoinType.INNER);
            return cb.like(cb.upper(joinAuthor.get("name")), "%" + authorName.toUpperCase() + "%");
        };
    }

}
