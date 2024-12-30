package com.mardoniodev.libraryapi.repository;

import com.mardoniodev.libraryapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT a FROM Author a " +
            "WHERE (:name IS NULL OR LOWER(a.name) LIKE :name) " +
            "AND (:nationality IS NULL OR LOWER(a.nationality) LIKE :nationality)")
    List<Author> findByNameAndNationality(@Param("name") String name,
                                          @Param("nationality") String nationality);

}
