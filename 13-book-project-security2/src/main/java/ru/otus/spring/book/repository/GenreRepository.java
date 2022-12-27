package ru.otus.spring.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.book.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.title = :title")
    Genre getByTitle(String title);

}
